package com.vroomerp.rest;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.beanutils.BeanUtils;

import com.vroomerp.common.dto.auto.AutoBean;
import com.vroomerp.common.dto.auto.AutoRequest;
import com.vroomerp.common.dto.auto.AutoResponse;
import com.vroomerp.common.dto.mezzo.MezzoBean;
import com.vroomerp.common.dto.moto.MotoBean;
import com.vroomerp.common.dto.moto.MotoRequest;
import com.vroomerp.common.dto.moto.MotoResponse;
import com.vroomerp.common.dto.tipologiche.MotoreEuroBean;
import com.vroomerp.common.dto.tipologiche.MotoreEuroResponse;
import com.vroomerp.common.dto.tipologiche.TipoAlimentazioneBean;
import com.vroomerp.common.dto.tipologiche.TipoAlimentazioneResponse;
import com.vroomerp.common.dto.tipologiche.TipoAutoBean;
import com.vroomerp.common.dto.tipologiche.TipoAutoResponse;
import com.vroomerp.common.dto.user.RuoloUtenteBean;
import com.vroomerp.common.dto.user.RuoloUtenteResponse;
import com.vroomerp.common.dto.user.UserBean;
import com.vroomerp.common.dto.user.UserResponse;
import com.vroomerp.ejb.AutomezziEJBInterface;
import com.vroomerp.ejb.UserEJBInterface;
import com.vroomerp.model.TAuto;
import com.vroomerp.model.TMezzo;
import com.vroomerp.model.TMoto;
import com.vroomerp.model.TMotoreEuro;
import com.vroomerp.model.TRuoloUtente;
import com.vroomerp.model.TTipoAlimentazione;
import com.vroomerp.model.TTipoAuto;
import com.vroomerp.model.TTipoMoto;
import com.vroomerp.model.TTipoMotore;
import com.vroomerp.security.Admin;
import com.vroomerp.security.JWTTokenNeeded;

import io.jsonwebtoken.lang.Strings;

@Stateless
@Path("/automezzi")
public class AutomezziRest {

	private static final Logger logger = Logger.getLogger(UserRest.class.getName());

	@EJB
	UserEJBInterface userEJB;
	// TVeicoloGruppo/TVeicoloGruppoMezzo creare tabella e informarsi/pensare in che
	// modo utilizzarla

	@EJB
	AutomezziEJBInterface automezziEJB;

	@Context
	HttpServletRequest httpRequest;

	@Path("/insertAuto")
	@JWTTokenNeeded
	@Admin
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	public Response insertAuto(AutoRequest request) {
		AutoResponse response = new AutoResponse();
		try {

			TAuto auto = new TAuto();

			auto.setNumeroPosti(request.getAutoBean().getNumeroPosti());
			auto.setNumeroPorte(request.getAutoBean().getNumeroPorte());
			auto.setPotenzaKw(request.getAutoBean().getPotenzaKw());
			auto.setCilindrata(request.getAutoBean().getCilindrata());
			auto.setConsumoMedio(request.getAutoBean().getConsumoMedio());// double

			TTipoAlimentazione tipoAlimentazione = automezziEJB
					.findByTipoAlimentazioneId(request.getAutoBean().getExtTipoAlimentazioneId());
			if (tipoAlimentazione == null) {
				response.setErrorCode(01);
				response.setErrorMessage("Tipo alimentazione inseistente");
				return Response.ok(response).build();
			}

			auto.setExtTipoAlimentazioneId(tipoAlimentazione.getIdTipoAlimentazione());

			TMotoreEuro motoreEuro = automezziEJB.findByMotoreEuroId(request.getAutoBean().getExtMotoreEuro());
			if (motoreEuro == null) {
				response.setErrorCode(02);
				response.setErrorMessage("Motore euro inseistente");
				return Response.ok(response).build();
			}

			auto.setExtMotoreEuro(motoreEuro.getMotoreEuroId());

			TTipoAuto tipoAuto = automezziEJB.findByTipoAutoId(request.getAutoBean().getExtTipoAuto());
			if (tipoAuto == null) {
				response.setErrorCode(03);
				response.setErrorMessage("Tipologia di auto inseistente");
				return Response.ok(response).build();
			}
			auto.setExtTipoAuto(tipoAuto.getTipoAutoId());

			auto = automezziEJB.insertAuto(auto);

			TMezzo mezzo = new TMezzo();
			mezzo.setAutoId(auto.getAutoId());
			mezzo.setAnnoImmatricolazione(request.getMezzoBean().getAnnoImmatricolazione());
			mezzo.setColore(request.getMezzoBean().getColore());
			if (request.getMezzoBean().getDataAcquisto() != null) {
				mezzo.setDataAcquisto(request.getMezzoBean().getDataAcquisto());
			}
			mezzo.setFlagDeleted(0);
			mezzo.setDataInserimento(new Date());
			mezzo.setExtTipoAlimentazione(auto.getExtTipoAlimentazioneId());
			mezzo.setMarca(request.getMezzoBean().getMarca());
			mezzo.setModello(request.getMezzoBean().getModello());
			mezzo.setTarga(request.getMezzoBean().getTarga());
			mezzo.setPrezzo(request.getMezzoBean().getPrezzo());

			mezzo = automezziEJB.insertMezzo(mezzo);

			MezzoBean mezzoBean = new MezzoBean();
			BeanUtils.copyProperties(mezzoBean, mezzo);
			AutoBean autoBean = new AutoBean();
			BeanUtils.copyProperties(autoBean, auto);

			autoBean.setMezzoBean(mezzoBean);

			response.setAutoBean(autoBean);
			response.setErrorCode(0);
			response.setErrorMessage("Automezzo creato con successo");

			return Response.ok(response).build();

		} catch (Exception e) {
			e.printStackTrace();
			response.setErrorCode(99);
			response.setErrorMessage("Errore interno del server");
			return Response.ok(response).build();
		}

	}

	@Path("/updateAuto")
	@JWTTokenNeeded
	@Admin
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	public Response updateAuto(AutoRequest request) {
		AutoResponse response = new AutoResponse();
		try {

			TAuto auto = automezziEJB.findByAutoId(request.getAutoBean().getAutoId());
			if (auto == null) {
				response.setErrorCode(1);
				response.setErrorMessage("Auto mancante");
				return Response.ok(response).build();
			}
			auto.setNumeroPosti(request.getAutoBean().getNumeroPosti());
			auto.setNumeroPorte(request.getAutoBean().getNumeroPorte());
			auto.setPotenzaKw(request.getAutoBean().getPotenzaKw());
			auto.setCilindrata(request.getAutoBean().getCilindrata());
			auto.setConsumoMedio(request.getAutoBean().getConsumoMedio());// double

			TTipoAlimentazione tipoAlimentazione = automezziEJB
					.findByTipoAlimentazioneId(request.getAutoBean().getExtTipoAlimentazioneId());
			if (tipoAlimentazione == null) {
				response.setErrorCode(01);
				response.setErrorMessage("Tipo alimentazione inseistente");
				return Response.ok(response).build();
			}

			auto.setExtTipoAlimentazioneId(tipoAlimentazione.getIdTipoAlimentazione());

			TMotoreEuro motoreEuro = automezziEJB.findByMotoreEuroId(request.getAutoBean().getExtMotoreEuro());
			if (motoreEuro == null) {
				response.setErrorCode(02);
				response.setErrorMessage("Motore euro inseistente");
				return Response.ok(response).build();
			}

			auto.setExtMotoreEuro(motoreEuro.getMotoreEuroId());

			TTipoAuto tipoAuto = automezziEJB.findByTipoAutoId(request.getAutoBean().getExtTipoAuto());
			if (tipoAuto == null) {
				response.setErrorCode(03);
				response.setErrorMessage("Tipologia di auto inseistente");
				return Response.ok(response).build();
			}
			auto.setExtTipoAuto(tipoAuto.getTipoAutoId());

			auto = automezziEJB.updateAuto(auto);

			TMezzo mezzo = automezziEJB.findMezzoByAutoId(auto.getAutoId());
			if (mezzo != null) {
				mezzo.setAutoId(auto.getAutoId());
				mezzo.setAnnoImmatricolazione(request.getMezzoBean().getAnnoImmatricolazione());
				mezzo.setColore(request.getMezzoBean().getColore());
				if (request.getMezzoBean().getDataAcquisto() != null) {
					mezzo.setDataAcquisto(request.getMezzoBean().getDataAcquisto());
				}
				// mezzo.setDataInserimento(new Date());
				mezzo.setExtTipoAlimentazione(auto.getExtTipoAlimentazioneId());
				mezzo.setMarca(request.getMezzoBean().getMarca());
				mezzo.setModello(request.getMezzoBean().getModello());
				mezzo.setTarga(request.getMezzoBean().getTarga());
				mezzo.setPrezzo(request.getMezzoBean().getPrezzo());

				mezzo = automezziEJB.updateMezzo(mezzo);
			}
			MezzoBean mezzoBean = new MezzoBean();
			BeanUtils.copyProperties(mezzoBean, mezzo);
			AutoBean autoBean = new AutoBean();
			BeanUtils.copyProperties(autoBean, auto);

			autoBean.setMezzoBean(mezzoBean);

			response.setAutoBean(autoBean);
			response.setErrorCode(0);
			response.setErrorMessage("Automezzo modificato con successo");

			return Response.ok(response).build();

		} catch (Exception e) {
			e.printStackTrace();
			response.setErrorCode(99);
			response.setErrorMessage("Errore interno del server");
			return Response.ok(response).build();
		}
	}

	@JWTTokenNeeded
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/findAllAuto")
	public Response findAllAuto() {
		AutoResponse response = new AutoResponse();

		try {

			List<AutoBean> autoList = automezziEJB.findAllAuto().stream().map(u -> {

				AutoBean bean = new AutoBean();
				try {
					BeanUtils.copyProperties(bean, u);
				} catch (IllegalAccessException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				TTipoAlimentazione tipoAlimentazione = automezziEJB
						.findByTipoAlimentazioneId(u.getExtTipoAlimentazioneId());
				if (tipoAlimentazione != null) {
					bean.setAlimentazione(tipoAlimentazione.getDescrizione());
				}

				TMotoreEuro motoreEuro = automezziEJB.findByMotoreEuroId(u.getExtMotoreEuro());
				if (motoreEuro != null) {
					bean.setMotoreEuro(motoreEuro.getDescrizione());
				}

				TTipoAuto tipoAuto = automezziEJB.findByTipoAutoId(u.getExtTipoAuto());
				if (tipoAuto != null) {
					bean.setDescTipoAuto(tipoAuto.getDescrizione());
				}

				TMezzo mezzo = automezziEJB.findMezzoByAutoId(u.getAutoId());
				if (mezzo != null) {

					MezzoBean mezzoBean = new MezzoBean();
					try {
						BeanUtils.copyProperties(mezzoBean, mezzo);
						bean.setMezzoBean(mezzoBean);
					} catch (IllegalAccessException | InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				return bean;

			}).collect(Collectors.toList());

			response.setAutoBeanList(autoList);
			response.setErrorCode(0);
			response.setErrorMessage("OK");

			return Response.ok(response).build();

		} catch (Exception e) {
			e.printStackTrace();
			response.setErrorCode(99);
			response.setErrorMessage("Errore interno del server");
			return Response.ok(response).build();
		}
	}

	@JWTTokenNeeded
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/findAutoById/{autoId}")
	public Response findAutoById(@PathParam("autoId") Integer autoId) {
		AutoResponse response = new AutoResponse();

		try {

			TAuto auto = automezziEJB.findByAutoId(autoId);
			if (auto == null) {
				response.setErrorCode(99);
				response.setErrorMessage("Automobile non trovata");
				return Response.ok(response).build();
			}
			AutoBean bean = new AutoBean();
			BeanUtils.copyProperties(bean, auto);
			
			TTipoAlimentazione tipoAlimentazione = automezziEJB
					.findByTipoAlimentazioneId(auto.getExtTipoAlimentazioneId());
			if (tipoAlimentazione != null) {
				bean.setAlimentazione(tipoAlimentazione.getDescrizione());
			}

			TMotoreEuro motoreEuro = automezziEJB.findByMotoreEuroId(auto.getExtMotoreEuro());
			if (motoreEuro != null) {
				bean.setMotoreEuro(motoreEuro.getDescrizione());
			}

			TTipoAuto tipoAuto = automezziEJB.findByTipoAutoId(auto.getExtTipoAuto());
			if (tipoAuto != null) {
				bean.setDescTipoAuto(tipoAuto.getDescrizione());
			}
			
			TMezzo mezzo = automezziEJB.findMezzoByAutoId(auto.getAutoId());
			if (mezzo != null) {

				MezzoBean mezzoBean = new MezzoBean();

				BeanUtils.copyProperties(mezzoBean, mezzo);
				bean.setMezzoBean(mezzoBean);
			}
			response.setAutoBean(bean);
			response.setErrorCode(0);
			response.setErrorMessage("OK");

			return Response.ok(response).build();

		} catch (Exception e) {
			e.printStackTrace();
			response.setErrorCode(99);
			response.setErrorMessage("Errore interno del server");
			return Response.ok(response).build();
		}
	}

	@Admin
	@JWTTokenNeeded
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/deleteAuto/{autoId}")
	public Response deleteAuto(@PathParam("autoId") Integer autoId) {
		AutoResponse response = new AutoResponse();

		try {

			TAuto auto = automezziEJB.findByAutoId(autoId);
			if (auto == null) {
				response.setErrorCode(99);
				response.setErrorMessage("Automobile non trovata");
				return Response.ok(response).build();
			}
			AutoBean bean = new AutoBean();
			BeanUtils.copyProperties(bean, auto);

			TMezzo mezzo = automezziEJB.findMezzoByAutoId(auto.getAutoId());
			if (mezzo != null) {
				MezzoBean mezzoBean = new MezzoBean();
				BeanUtils.copyProperties(mezzoBean, mezzo);
				bean.setMezzoBean(mezzoBean);
			}

			automezziEJB.deleteAuto(auto);

			response.setAutoBean(bean);
			response.setErrorCode(0);
			response.setErrorMessage("Automobile cancellata correttamente");

			return Response.ok(response).build();

		} catch (Exception e) {
			e.printStackTrace();
			response.setErrorCode(99);
			response.setErrorMessage("Errore interno del server");
			return Response.ok(response).build();
		}
	}

	@JWTTokenNeeded
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/findAllTipoAlimentazione")
	public Response findAllTipoAlimentazione() {
		TipoAlimentazioneResponse response = new TipoAlimentazioneResponse();

		try {

			List<TipoAlimentazioneBean> alimentazioneBeanList = automezziEJB.findAllTipoAlimentazione().stream().map(u -> {

				TipoAlimentazioneBean bean = new TipoAlimentazioneBean();
				try {
					BeanUtils.copyProperties(bean, u);
				} catch (IllegalAccessException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return bean;

			}).collect(Collectors.toList());

			response.setTipoAlimentazioneBeanList(alimentazioneBeanList);
			response.setErrorCode(0);
			response.setErrorMessage("OK");

			return Response.ok(response).build();

		} catch (Exception e) {
			e.printStackTrace();
			response.setErrorCode(99);
			response.setErrorMessage("Errore interno del server");
			return Response.ok(response).build();
		}
	}
	
	@JWTTokenNeeded
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/findAllMotoreEuro")
	public Response findAllMotoreEuro() {
		MotoreEuroResponse response = new MotoreEuroResponse();

		try {

			List<MotoreEuroBean> motoreEuroBeanList =automezziEJB.findAllMotoreEuro().stream().map(u -> {

				MotoreEuroBean bean = new MotoreEuroBean();
				try {
					BeanUtils.copyProperties(bean, u);
				} catch (IllegalAccessException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return bean;

			}).collect(Collectors.toList());

			response.setMotoreEuroBeanList(motoreEuroBeanList);
			response.setErrorCode(0);
			response.setErrorMessage("OK");

			return Response.ok(response).build();

		} catch (Exception e) {
			e.printStackTrace();
			response.setErrorCode(99);
			response.setErrorMessage("Errore interno del server");
			return Response.ok(response).build();
		}
	}
	
	@JWTTokenNeeded
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/findAllTipoAuto")
	public Response findAllTipoAuto() {
		TipoAutoResponse response = new TipoAutoResponse();

		try {

			List<TipoAutoBean> tipoAutoBeanList =automezziEJB.findAllTipoAuto().stream().map(u -> {

				TipoAutoBean bean = new TipoAutoBean();
				try {
					BeanUtils.copyProperties(bean, u);
				} catch (IllegalAccessException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return bean;

			}).collect(Collectors.toList());

			response.setTipoAutoBeanList(tipoAutoBeanList);
			response.setErrorCode(0);
			response.setErrorMessage("OK");

			return Response.ok(response).build();

		} catch (Exception e) {
			e.printStackTrace();
			response.setErrorCode(99);
			response.setErrorMessage("Errore interno del server");
			return Response.ok(response).build();
		}
	}
	
	@Path("/insertMoto")
	@JWTTokenNeeded
	@Admin
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	public Response insertMoto(MotoRequest request) {
		MotoResponse response = new MotoResponse();
		try {

			TMoto moto = new TMoto();


			String peso = request.getMotoBean().getPeso();
			if (!peso.toLowerCase().contains("kg")) {
			    peso += " kg";
			}
			moto.setPeso(peso);
			moto.setPotenzaKw(request.getMotoBean().getPotenzaKw());
			moto.setCilindrata(request.getMotoBean().getCilindrata());
			moto.setRaffreddamento(request.getMotoBean().getRaffreddamento());

			TTipoAlimentazione tipoAlimentazione = automezziEJB
					.findByTipoAlimentazioneId(request.getMotoBean().getExtTipoAlimentazione());
			if (tipoAlimentazione == null) {
				response.setErrorCode(01);
				response.setErrorMessage("Tipo alimentazione inseistente");
				return Response.ok(response).build();
			}

			moto.setExtTipoAlimentazione(tipoAlimentazione.getIdTipoAlimentazione());
			
			TTipoMotore tipoMotore = automezziEJB
					.findByTipoMotoreId(request.getMotoBean().getExtTipoMotoreId());
			if (tipoMotore == null) {
				response.setErrorCode(01);
				response.setErrorMessage("Tipo alimentazione inseistente");
				return Response.ok(response).build();
			}
			
			moto.setExtTipoMotoreId(tipoMotore.getTipoMotoreId());
			
			TMotoreEuro motoreEuro = automezziEJB.findByMotoreEuroId(request.getMotoBean().getExtMotoreEuroId());
			if (motoreEuro == null) {
				response.setErrorCode(02);
				response.setErrorMessage("Motore euro inseistente");
				return Response.ok(response).build();
			}

			moto.setExtMotoreEuroId(motoreEuro.getMotoreEuroId());

			TTipoMoto tipoMoto = automezziEJB.findByTipoMotoId(request.getMotoBean().getExtTipoMotoId());
			if (tipoMoto == null) {
				response.setErrorCode(03);
				response.setErrorMessage("Tipologia di moto inseistente");
				return Response.ok(response).build();
			}
			moto.setExtTipoMotoId(tipoMoto.getTipoMotoId());

			moto = automezziEJB.insertMoto(moto);

			TMezzo mezzo = new TMezzo();
			mezzo.setMotoId(moto.getMotoId());
			mezzo.setAnnoImmatricolazione(request.getMezzoBean().getAnnoImmatricolazione());
			mezzo.setColore(request.getMezzoBean().getColore());
			if (request.getMezzoBean().getDataAcquisto() != null) {
				mezzo.setDataAcquisto(request.getMezzoBean().getDataAcquisto());
			}
			mezzo.setFlagDeleted(0);
			mezzo.setDataInserimento(new Date());
			mezzo.setExtTipoAlimentazione(moto.getExtTipoAlimentazione());
			mezzo.setMarca(request.getMezzoBean().getMarca());
			mezzo.setModello(request.getMezzoBean().getModello());
			mezzo.setTarga(request.getMezzoBean().getTarga());
			mezzo.setPrezzo(request.getMezzoBean().getPrezzo());

			mezzo = automezziEJB.insertMezzo(mezzo);

			MezzoBean mezzoBean = new MezzoBean();
			BeanUtils.copyProperties(mezzoBean, mezzo);
			MotoBean motoBean = new MotoBean();
			BeanUtils.copyProperties(motoBean, moto);

			motoBean.setMezzoBean(mezzoBean);

			response.setMotoBean(motoBean);
			response.setErrorCode(0);
			response.setErrorMessage("Moto creata con successo");

			return Response.ok(response).build();

		} catch (Exception e) {
			e.printStackTrace();
			response.setErrorCode(99);
			response.setErrorMessage("Errore interno del server");
			return Response.ok(response).build();
		}

	}
	
	@Path("/updateMoto")
	@JWTTokenNeeded
	@Admin
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	public Response updateMoto(MotoRequest request) {
		MotoResponse response = new MotoResponse();
		try {

			TMoto moto = automezziEJB.findByMotoId(request.getMotoBean().getMotoId());


			String peso = request.getMotoBean().getPeso();
			if (!peso.toLowerCase().contains("kg")) {
			    peso += " kg";
			}
			moto.setPeso(peso);
			moto.setPotenzaKw(request.getMotoBean().getPotenzaKw());
			moto.setCilindrata(request.getMotoBean().getCilindrata());
			moto.setRaffreddamento(request.getMotoBean().getRaffreddamento());

			TTipoAlimentazione tipoAlimentazione = automezziEJB
					.findByTipoAlimentazioneId(request.getMotoBean().getExtTipoAlimentazione());
			if (tipoAlimentazione == null) {
				response.setErrorCode(01);
				response.setErrorMessage("Tipo alimentazione inseistente");
				return Response.ok(response).build();
			}

			moto.setExtTipoAlimentazione(tipoAlimentazione.getIdTipoAlimentazione());
			
			TTipoMotore tipoMotore = automezziEJB
					.findByTipoMotoreId(request.getMotoBean().getExtTipoMotoreId());
			if (tipoMotore == null) {
				response.setErrorCode(01);
				response.setErrorMessage("Tipo alimentazione inseistente");
				return Response.ok(response).build();
			}
			
			moto.setExtTipoMotoreId(tipoMotore.getTipoMotoreId());
			
			TMotoreEuro motoreEuro = automezziEJB.findByMotoreEuroId(request.getMotoBean().getExtMotoreEuroId());
			if (motoreEuro == null) {
				response.setErrorCode(02);
				response.setErrorMessage("Motore euro inseistente");
				return Response.ok(response).build();
			}

			moto.setExtMotoreEuroId(motoreEuro.getMotoreEuroId());

			TTipoMoto tipoMoto = automezziEJB.findByTipoMotoId(request.getMotoBean().getExtTipoMotoId());
			if (tipoMoto == null) {
				response.setErrorCode(03);
				response.setErrorMessage("Tipologia di moto inseistente");
				return Response.ok(response).build();
			}
			moto.setExtTipoMotoId(tipoMoto.getTipoMotoId());

			moto = automezziEJB.updateMoto(moto);

			TMezzo mezzo = automezziEJB.findMezzoByMotoId(moto.getMotoId());
			mezzo.setMotoId(moto.getMotoId());
			mezzo.setAnnoImmatricolazione(request.getMezzoBean().getAnnoImmatricolazione());
			mezzo.setColore(request.getMezzoBean().getColore());
			if (request.getMezzoBean().getDataAcquisto() != null) {
				mezzo.setDataAcquisto(request.getMezzoBean().getDataAcquisto());
			}
			mezzo.setFlagDeleted(0);
			//mezzo.setDataInserimento(new Date());
			mezzo.setExtTipoAlimentazione(moto.getExtTipoAlimentazione());
			mezzo.setMarca(request.getMezzoBean().getMarca());
			mezzo.setModello(request.getMezzoBean().getModello());
			mezzo.setTarga(request.getMezzoBean().getTarga());
			mezzo.setPrezzo(request.getMezzoBean().getPrezzo());

			mezzo = automezziEJB.updateMezzo(mezzo);

			MezzoBean mezzoBean = new MezzoBean();
			BeanUtils.copyProperties(mezzoBean, mezzo);
			MotoBean motoBean = new MotoBean();
			BeanUtils.copyProperties(motoBean, moto);

			motoBean.setMezzoBean(mezzoBean);

			response.setMotoBean(motoBean);
			response.setErrorCode(0);
			response.setErrorMessage("Moto Aggiornata con successo");

			return Response.ok(response).build();

		} catch (Exception e) {
			e.printStackTrace();
			response.setErrorCode(99);
			response.setErrorMessage("Errore interno del server");
			return Response.ok(response).build();
		}
	}
	
	@Admin
	@JWTTokenNeeded
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/deleteMoto/{motoId}")
	public Response deleteMoto(@PathParam("motoId") Integer motoId) {
		AutoResponse response = new AutoResponse();

		try {

			TMoto moto = automezziEJB.findByMotoId(motoId);
			if (moto == null) {
				response.setErrorCode(99);
				response.setErrorMessage("Moto non trovata");
				return Response.ok(response).build();
			}
			AutoBean bean = new AutoBean();
			BeanUtils.copyProperties(bean, moto);

			TMezzo mezzo = automezziEJB.findMezzoByMotoId(moto.getMotoId());
			if (mezzo != null) {
				MezzoBean mezzoBean = new MezzoBean();
				BeanUtils.copyProperties(mezzoBean, mezzo);
				bean.setMezzoBean(mezzoBean);
			}

			automezziEJB.deleteMoto(moto);

			response.setAutoBean(bean);
			response.setErrorCode(0);
			response.setErrorMessage("Moto cancellata correttamente");

			return Response.ok(response).build();

		} catch (Exception e) {
			e.printStackTrace();
			response.setErrorCode(99);
			response.setErrorMessage("Errore interno del server");
			return Response.ok(response).build();
		}
	}
	
	@JWTTokenNeeded
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/findMotoById/{motoId}")
	public Response findMotoById(@PathParam("motoId") Integer motoId) {
		MotoResponse response = new MotoResponse();

		try {

			TMoto moto = automezziEJB.findByMotoId(motoId);
			if (moto == null) {
				response.setErrorCode(99);
				response.setErrorMessage("Moto non trovata");
				return Response.ok(response).build();
			}
			MotoBean bean = new MotoBean();
			BeanUtils.copyProperties(bean, moto);
			
			TTipoAlimentazione tipoAlimentazione = automezziEJB
					.findByTipoAlimentazioneId(moto.getExtTipoAlimentazione());
			if (tipoAlimentazione != null) {
				bean.setAlimentazione(tipoAlimentazione.getDescrizione());
			}

			TMotoreEuro motoreEuro = automezziEJB.findByMotoreEuroId(moto.getExtMotoreEuroId());
			if (motoreEuro != null) {
				bean.setMotoreEuro(motoreEuro.getDescrizione());
			}

			TTipoMoto tipoMoto = automezziEJB.findByTipoMotoId(moto.getExtTipoMotoId());
			if (tipoMoto != null) {
				bean.setTipoMoto(tipoMoto.getDescrizione());
			}
			
			TTipoMotore tipoMotore = automezziEJB.findByTipoMotoreId(moto.getExtTipoMotoreId());
			if (tipoMotore != null) {
				bean.setTipoMotore(tipoMotore.getDescrizione());
			}
			
			TMezzo mezzo = automezziEJB.findMezzoByMotoId(moto.getMotoId());
			if (mezzo != null) {

				MezzoBean mezzoBean = new MezzoBean();

				BeanUtils.copyProperties(mezzoBean, mezzo);
				bean.setMezzoBean(mezzoBean);
			}
			response.setMotoBean(bean);
			response.setErrorCode(0);
			response.setErrorMessage("OK");

			return Response.ok(response).build();

		} catch (Exception e) {
			e.printStackTrace();
			response.setErrorCode(99);
			response.setErrorMessage("Errore interno del server");
			return Response.ok(response).build();
		}
	}
	
	@JWTTokenNeeded
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/findAllMoto")
	public Response findAllMoto() {
		MotoResponse response = new MotoResponse();

		try {

			List<MotoBean> motoList = automezziEJB.findAllMoto().stream().map(u -> {
	
			MotoBean bean = new MotoBean();
			try {
				BeanUtils.copyProperties(bean, u);
			} catch (IllegalAccessException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			TTipoAlimentazione tipoAlimentazione = automezziEJB
					.findByTipoAlimentazioneId(u.getExtTipoAlimentazione());
			if (tipoAlimentazione != null) {
				bean.setAlimentazione(tipoAlimentazione.getDescrizione());
			}

			TMotoreEuro motoreEuro = automezziEJB.findByMotoreEuroId(u.getExtMotoreEuroId());
			if (motoreEuro != null) {
				bean.setMotoreEuro(motoreEuro.getDescrizione());
			}

			TTipoMoto tipoMoto = automezziEJB.findByTipoMotoId(u.getExtTipoMotoId());
			if (tipoMoto != null) {
				bean.setTipoMoto(tipoMoto.getDescrizione());
			}
			
			TTipoMotore tipoMotore = automezziEJB.findByTipoMotoreId(u.getExtTipoMotoreId());
			if (tipoMotore != null) {
				bean.setTipoMotore(tipoMotore.getDescrizione());
			}
			
			TMezzo mezzo = automezziEJB.findMezzoByMotoId(u.getMotoId());
			if (mezzo != null) {

				MezzoBean mezzoBean = new MezzoBean();

				try {
					BeanUtils.copyProperties(mezzoBean, mezzo);
				} catch (IllegalAccessException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bean.setMezzoBean(mezzoBean);
			}
			return bean;
		
			}).collect(Collectors.toList());
			response.setListaMoto(motoList);
			response.setErrorCode(0);
			response.setErrorMessage("OK");

			return Response.ok(response).build();

		} catch (Exception e) {
			e.printStackTrace();
			response.setErrorCode(99);
			response.setErrorMessage("Errore interno del server");
			return Response.ok(response).build();
		}
	}
}
