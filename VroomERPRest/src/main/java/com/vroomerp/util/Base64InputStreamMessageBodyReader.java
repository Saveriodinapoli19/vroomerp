package com.vroomerp.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Base64;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;

import org.apache.commons.fileupload.MultipartStream;
import org.apache.log4j.Logger;

@Consumes(MediaType.MULTIPART_FORM_DATA)
public class Base64InputStreamMessageBodyReader implements MessageBodyReader<InputStream> {

	private static Logger logger = Logger.getLogger(Base64InputStreamMessageBodyReader.class);

	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return InputStream.class.isAssignableFrom(type);
	}

	@Override
	public InputStream readFrom(Class<InputStream> type, Type genericType, Annotation[] annotations,
			MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws IOException, WebApplicationException {

		try {
			// Controlliamo se il tipo di media è multipart
			if (!MediaType.MULTIPART_FORM_DATA.equals(mediaType)) {
				throw new WebApplicationException("Il tipo di media non è multipart");
			}

			byte[] boundary = getBoundary(httpHeaders);

			@SuppressWarnings("deprecation")
			MultipartStream multipartStream = new MultipartStream(entityStream, boundary);
			boolean nextPart = multipartStream.skipPreamble();
			if (!nextPart) {
				throw new WebApplicationException("Preamble not found in the multipart stream");
			}

			// ByteArrayOutputStream per memorizzare i dati multipart
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

			// Leggiamo i dati multipart
			while (nextPart) {
				String headers = multipartStream.readHeaders();
				multipartStream.readBodyData(outputStream);
				nextPart = multipartStream.readBoundary();
			}

			// Restituisci l'input stream dei dati multipart letti
			byte[] decodedBytes = Base64.getDecoder().decode(outputStream.toByteArray());
			return new ByteArrayInputStream(decodedBytes);
		} catch (IOException e) {
			throw new WebApplicationException("Errore durante la lettura del flusso multipart: " + e.getMessage(), e);
		}
	}

	private byte[] getBoundary(MultivaluedMap<String, String> headers) {
		String contentType = headers.getFirst("Content-Type");
		String[] parts = contentType.split("; boundary=");
		if (parts.length != 2) {
			throw new IllegalArgumentException("Invalid Content-Type header: no boundary found");
		}
		return parts[1].getBytes();
	}

}