package com.vroomerp.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vroomerp.common.util.StringUtils;



@Stateless
@Path("xls")
public class XLSGeneratorEJB {

	static Logger logger = LoggerFactory.getLogger(XLSGeneratorEJB.class);

//	@EJB
//	AvvisiRest avvisiRest;

	public byte[] generateFromAnnotatedList(List<?> excelObjectList, String sheetName)
			throws IOException, IllegalArgumentException, IllegalAccessException {

		byte[] excelByte = null;

		if (excelObjectList.size() > 0) {

			@SuppressWarnings("rawtypes")
			Class clasz = excelObjectList.get(0).getClass();

			Field[] declaredFieldArray = clasz.getDeclaredFields();

			HSSFWorkbook workBook = new HSSFWorkbook();

			try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

				int rowCount = 0;

				HSSFSheet sheet = workBook.createSheet(sheetName);

				HSSFRow rowHead = sheet.createRow(rowCount++);

				int count = 0;
				for (Field f : declaredFieldArray) {

					String name = f.getName().toUpperCase();
					if (f.isAnnotationPresent(XLSMapping.class)) {
						XLSMapping xlsMappingAnnotation = f.getAnnotation(XLSMapping.class);
						if (xlsMappingAnnotation.exclude()) {
							continue;
						}

						if (!xlsMappingAnnotation.explicitName().equals("")) {
							name = xlsMappingAnnotation.explicitName();
						}

					}

					rowHead.createCell(count++).setCellValue(name);

				}

				for (Object bindObject : excelObjectList) {

					HSSFRow row = sheet.createRow(rowCount++);

					count = 0;

					for (Field f : declaredFieldArray) {

						f.setAccessible(true);

						Object valueObject = f.get(bindObject);
						String valueString = valueObject != null ? "" + valueObject : "-";

						if (f.isAnnotationPresent(XLSMapping.class)) {
							XLSMapping xlsMappingAnnotation = f.getAnnotation(XLSMapping.class);
							if (xlsMappingAnnotation.exclude()) {
								continue;
							}

							switch (xlsMappingAnnotation.converter()) {
							case XLSMapping.Converter.LONG_TO_DATE:

								valueString = valueObject != null
										? StringUtils.dateWithoutHourStringByMillis((long) valueObject)
										: "";

								break;

							case XLSMapping.Converter.LONG_TO_DATETIME:

								valueString = valueObject != null
										? StringUtils.dateWithHourStringByMillis((long) valueObject)
										: "";

								break;

							case XLSMapping.Converter.CENT_TO_EURO:

								valueString = valueObject != null
										? StringUtils.formatCentsToEuro(Integer.parseInt("" + valueObject))
										: "";

								break;

							case XLSMapping.Converter.REMOVE_CENT:

								valueString = valueObject != null
										? StringUtils.removeCent(Integer.parseInt("" + valueObject))
										: "";

							default:
								break;
							}

						}

						row.createCell(count++).setCellValue(valueString);

					}

				}

				count = 0;
				for (Field f : declaredFieldArray) {

					if (f.isAnnotationPresent(XLSMapping.class)) {
						XLSMapping xlsMappingAnnotation = f.getAnnotation(XLSMapping.class);
						if (xlsMappingAnnotation.exclude()) {
							continue;
						}
					}

					sheet.autoSizeColumn(count++);

				}

				workBook.write(bos);

				excelByte = bos.toByteArray();

				workBook.close();

			}

		}

		return excelByte;

	}

	public byte[] generateFromMultipleAnnotatedList(List<List<?>> sheetsList, List<String> sheetNameList)
			throws IOException, IllegalArgumentException, IllegalAccessException {

		byte[] excelByte = null;

		if (sheetsList.size() > 0) {

			HSSFWorkbook workBook = new HSSFWorkbook();

			try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

				int sheetIndex = 0;

				for (List<?> excelObjectList : sheetsList) {

					@SuppressWarnings("rawtypes")
					Class clasz = excelObjectList.get(0).getClass();

					Field[] declaredFieldArray = clasz.getDeclaredFields();

					int rowCount = 0;

					HSSFSheet sheet = workBook.createSheet(sheetNameList.get(sheetIndex));

					HSSFRow rowHead = sheet.createRow(rowCount++);

					int count = 0;
					for (Field f : declaredFieldArray) {

						String name = f.getName().toUpperCase();
						if (f.isAnnotationPresent(XLSMapping.class)) {
							XLSMapping xlsMappingAnnotation = f.getAnnotation(XLSMapping.class);
							if (xlsMappingAnnotation.exclude()) {
								continue;
							}

							if (!xlsMappingAnnotation.explicitName().equals("")) {
								name = xlsMappingAnnotation.explicitName();
							}

						}

						rowHead.createCell(count++).setCellValue(name);

					}

					for (Object bindObject : excelObjectList) {

						HSSFRow row = sheet.createRow(rowCount++);

						count = 0;

						for (Field f : declaredFieldArray) {

							f.setAccessible(true);

							Object valueObject = f.get(bindObject);
							String valueString = valueObject != null ? "" + valueObject : "-";

							if (f.isAnnotationPresent(XLSMapping.class)) {
								XLSMapping xlsMappingAnnotation = f.getAnnotation(XLSMapping.class);
								if (xlsMappingAnnotation.exclude()) {
									continue;
								}

								switch (xlsMappingAnnotation.converter()) {
								case XLSMapping.Converter.LONG_TO_DATE:

									valueString = valueObject != null
											? StringUtils.dateWithoutHourStringByMillis((long) valueObject)
											: "";

									break;

								case XLSMapping.Converter.LONG_TO_DATETIME:

									valueString = valueObject != null
											? StringUtils.dateWithHourStringByMillis((long) valueObject)
											: "";

									break;

								case XLSMapping.Converter.CENT_TO_EURO:

									valueString = valueObject != null
											? StringUtils.formatCentsToEuro(Integer.parseInt("" + valueObject))
											: "";

									break;

								case XLSMapping.Converter.REMOVE_CENT:

									valueString = valueObject != null
											? StringUtils.removeCent(Integer.parseInt("" + valueObject))
											: "";

									break;

								default:
									break;
								}

							}

							row.createCell(count++).setCellValue(valueString);

						}

					}

					count = 0;
					for (Field f : declaredFieldArray) {

						if (f.isAnnotationPresent(XLSMapping.class)) {
							XLSMapping xlsMappingAnnotation = f.getAnnotation(XLSMapping.class);
							if (xlsMappingAnnotation.exclude()) {
								continue;
							}
						}

						sheet.autoSizeColumn(count++);

					}

					sheetIndex++;

				}

				workBook.write(bos);

				excelByte = bos.toByteArray();

			}

		}

		return excelByte;

	}

//	@GET
//	@Path("/test/{idAvviso}")
//	@Produces("application/vnd.ms-excel")
//	public Response test(@PathParam("idAvviso") Integer idAvviso) {
//		ResponseBuilder response = null;
//
//		try {
//
//			AvvisiResponse avvisiResponse = (AvvisiResponse) avvisiRest.findById(idAvviso).getEntity();
//
//			response = Response
//					.ok((Object) generaListaSondaggiXLS(avvisiResponse.getAvvisoRestBean().getListaSondaggi()));
//			response.header("Content-Disposition", "attachment; filename=sondaggio_avviso" + idAvviso + ".xls");
//			response.header("Content-Type", "application/vnd.ms-excel");
//
//			logger.info("Export test completato");
//
//		} catch (Exception ex) {
//
//			logger.error("ex name [" + ex.getClass().getName() + "] : " + ex.getMessage(), ex);
//			response = Response.status(401);
//
//		}
//
//		return response.build();
//
//	}
//
//	public byte[] generaListaSondaggiXLS(List<Sondaggio> listaSondaggi)
//			throws IllegalArgumentException, IllegalAccessException, IOException {
//
//		List<Object> elencoRecordXLS = new ArrayList<Object>();
//
//		for (Sondaggio sondaggio : listaSondaggi) {
//
//			Map<Integer, Anagrafica> destinatariSondaggioMap = sondaggio.getListaDestinatari().stream()
//					.collect(Collectors.toMap(Anagrafica::getAnagraficaId, Function.identity()));
//
//			for (Anagrafica anagraficaVotante : sondaggio.getListaVotanti()) {
//
//				destinatariSondaggioMap.get(anagraficaVotante.getAnagraficaId())
//						.setOpzioniSelezionate(anagraficaVotante.getOpzioniSelezionate());
//
//			}
//
//			destinatariSondaggioMap.values().forEach(ds -> {
//
//				SondaggioXLS st = new SondaggioXLS();
//
//				st.setIdSondaggio(sondaggio.getIdSondaggio());
//				st.setTitoloSondaggio(sondaggio.getTitolo());
//				st.setEmail(ds.getEmailPersonale());
//				st.setNomeCognome(ds.getNome() + " " + ds.getCognome());
//				st.setDataDiNascita(ds.getDataDiNascita().getTime());
//
//				if (ds.getOpzioniSelezionate() != null) {
//					st.setVoto(ds.getOpzioniSelezionate().get(0).getTestoOpzione());
//				}
//
//				elencoRecordXLS.add(st);
//
//			});
//
//		}
//
//		return generateFromAnnotatedList(elencoRecordXLS, "Destinatari");
//
//	}

//	@GET
//	@Path("/test/{idAvviso}")
//	@Produces("application/vnd.ms-excel")
//	public Response test(@PathParam("idAvviso") Integer idAvviso) {
//	    ResponseBuilder response = null;
//
//	    try {
//	        AvvisiResponse avvisiResponse = (AvvisiResponse) avvisiRest.findById(idAvviso).getEntity();
//	        byte[] fileContent = generaListaSondaggiXLS(avvisiResponse.getAvvisoRestBean().getListaSondaggi());
//
//	        if (fileContent == null || fileContent.length == 0) {
//	            logger.error("Errore: il file generato è vuoto.");
//	            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
//	                    .entity("Errore nella generazione del file: il file è vuoto.")
//	                    .build();
//	        }
//
//	        response = Response.ok((Object) fileContent);
//	        response.header("Content-Disposition", "attachment; filename=sondaggio_avviso" + idAvviso + ".xls");
//	        response.header("Content-Type", "application/vnd.ms-excel");
//
//	        logger.info("Export test completato");
//
//	    } catch (Exception ex) {
//	        logger.error("Errore nella generazione del file: " + ex.getMessage(), ex);
//	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
//	                .entity("Errore nella generazione del file: " + ex.getMessage())
//	                .build();
//	    }
//
//	    return response.build();
//	}

	
//	public byte[] generaListaSondaggiXLS(List<Sondaggio> listaSondaggi)
//	        throws IllegalArgumentException, IllegalAccessException, IOException {
//
//	    List<Object> elencoRecordXLS = new ArrayList<>();
//
//	    for (Sondaggio sondaggio : listaSondaggi) {
//	        Map<Integer, Anagrafica> destinatariSondaggioMap = sondaggio.getListaDestinatari().stream()
//	                .collect(Collectors.toMap(Anagrafica::getAnagraficaId, Function.identity()));
//
//	        for (Anagrafica anagraficaVotante : sondaggio.getListaVotanti()) {
//	            if (destinatariSondaggioMap.containsKey(anagraficaVotante.getAnagraficaId())) {
//	                destinatariSondaggioMap.get(anagraficaVotante.getAnagraficaId())
//	                        .setOpzioniSelezionate(anagraficaVotante.getOpzioniSelezionate());
//	            }
//	        }
//
//	        destinatariSondaggioMap.values().forEach(ds -> {
//	            SondaggioXLS st = new SondaggioXLS();
//	            st.setIdSondaggio(sondaggio.getIdSondaggio());
//	            st.setTitoloSondaggio(sondaggio.getTitolo());
//	            st.setEmail(ds.getEmailPersonale());
//	            st.setNomeCognome(ds.getNome() + " " + ds.getCognome());
//	            st.setDataDiNascita(ds.getDataDiNascita().getTime());
//
//	            if (ds.getOpzioniSelezionate() != null && !ds.getOpzioniSelezionate().isEmpty()) {
//	                st.setVoto(ds.getOpzioniSelezionate().get(0).getTestoOpzione());
//	            }
//
//	            elencoRecordXLS.add(st);
//	        });
//	    }
//
//	    byte[] fileContent = generateFromAnnotatedList(elencoRecordXLS, "Destinatari");
//
//	    if (fileContent == null || fileContent.length == 0) {
//	        throw new IOException("Errore nella generazione del file XLS: il file è vuoto.");
//	    }
//
//	    return fileContent;
//	}

}