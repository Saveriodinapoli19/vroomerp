package com.vroomerp.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.crypto.Data;

import org.apache.commons.beanutils.BeanUtils;

import com.vroomerp.common.dto.acquisti.AcquistiBean;
import com.vroomerp.common.dto.acquisti.AcquistiRequest;
import com.vroomerp.common.dto.acquisti.AcquistiResponse;
import com.vroomerp.common.dto.auto.AutoBean;
import com.vroomerp.common.dto.mezzo.MezzoBean;
import com.vroomerp.common.dto.moto.MotoBean;
import com.vroomerp.common.dto.tir.TirBean;
import com.vroomerp.common.dto.user.UserBean;
import com.vroomerp.common.dto.user.UserRequest;
import com.vroomerp.common.dto.user.UserResponse;
import com.vroomerp.common.util.PasswordUtil;
import com.vroomerp.ejb.AcquistiEJBInterface;
import com.vroomerp.ejb.AutomezziEJBInterface;
import com.vroomerp.ejb.UserEJBInterface;
import com.vroomerp.model.TAcquisti;
import com.vroomerp.model.TAuto;
import com.vroomerp.model.TMezzo;
import com.vroomerp.model.TMoto;
import com.vroomerp.model.TRuoloUtente;
import com.vroomerp.model.TTir;
import com.vroomerp.model.TUser;
import com.vroomerp.security.Admin;
import com.vroomerp.security.JWTTokenNeeded;
import com.vroomerp.security.*;

@Stateless
@Path("/acquisti")
public class AcquistiRest {

	@EJB
	UserEJBInterface userEJB;

	@EJB
	AutomezziEJBInterface automezziEJB;

	@Context
	HttpServletRequest httpRequest;

	@EJB
	AcquistiEJBInterface acquistiEJB;

	@JWTTokenNeeded
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/insertAcquisto")
	public Response insertAcquisto(AcquistiRequest request) {
		AcquistiResponse response = new AcquistiResponse();

		try {
			TAcquisti acquisti = new TAcquisti();

			String token = httpRequest.getHeader("Authorization");

			if (token == null || !token.startsWith("Bearer ")) {
				response.setErrorCode(401);
				response.setErrorMessage("Token non presente");
				return Response.ok(response).build();
			}

			String jwt = token.substring(7);
			Integer userId = Integer.parseInt(JwtUtil.validateToken(jwt)); // <-- Eccolo!

			TUser user = userEJB.findById(userId);
			if (user == null) {
				response.setErrorCode(99);
				response.setErrorMessage("Utente non trovato");
				return Response.ok(response).build();
			}

			if (user.getExtRuoloUtenteId() != 2) {
				response.setErrorCode(1);
				response.setErrorMessage("Per l'acquisto bisogna essere un utente con ruolo appropriato");
				return Response.ok(response).build();
			}

			acquisti.setExtUserId(user.getUserId());

			Integer autoId = request.getAcquistiBean().getExtAutoId();
			Integer motoId = request.getAcquistiBean().getExtMotoId();
			Integer tirId = request.getAcquistiBean().getExtTirId();

			if (autoId != null) {
				TAuto auto = automezziEJB.findByAutoId(autoId);
				if (auto == null) {
					response.setErrorCode(99);
					response.setErrorMessage("Auto non trovata");
					return Response.ok(response).build();
				}
				TMezzo mezzo = automezziEJB.findMezzoByAutoId(auto.getAutoId());
				if (mezzo != null) {
					mezzo.setFlagAcquistato(1);
					mezzo.setDataAcquisto(new Date());
					automezziEJB.updateMezzo(mezzo);
				}
				acquisti.setExtAutoId(auto.getAutoId());
			} else if (motoId != null) {
				TMoto moto = automezziEJB.findByMotoId(motoId);
				if (moto == null) {
					response.setErrorCode(99);
					response.setErrorMessage("Moto non trovata");
					return Response.ok(response).build();
				}
				TMezzo mezzo = automezziEJB.findMezzoByMotoId(moto.getMotoId());
				if (mezzo != null) {
					mezzo.setFlagAcquistato(1);
					mezzo.setDataAcquisto(new Date());
					automezziEJB.updateMezzo(mezzo);
				}
				acquisti.setExtMotoId(moto.getMotoId());
			} else if (tirId != null) {
				TTir tir = automezziEJB.findByTirId(tirId);
				if (tir == null) {
					response.setErrorCode(99);
					response.setErrorMessage("Tir non trovato");
					return Response.ok(response).build();
				}
				TMezzo mezzo = automezziEJB.findMezzoByTirId(tir.getTirId());
				if (mezzo != null) {
					mezzo.setFlagAcquistato(1);
					mezzo.setDataAcquisto(new Date());
					automezziEJB.updateMezzo(mezzo);
				}
				acquisti.setExtTirId(tir.getTirId());
			} else {
				response.setErrorCode(99);
				response.setErrorMessage("Nessun mezzo selezionato per l'acquisto");
				return Response.ok(response).build();
			}

			acquisti = acquistiEJB.insertAcquisto(acquisti);

			AcquistiBean acquistiBean = new AcquistiBean();
			BeanUtils.copyProperties(acquistiBean, acquisti);

			response.setAcquistiBean(acquistiBean);
			response.setErrorCode(0);
			response.setErrorMessage("Acquisto registrato con successo");

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
	@Path("findById/{acquistiId}")
	public Response findById(@PathParam("acquistiId") Integer acquistiId) {
	    AcquistiResponse response = new AcquistiResponse();

	    try {
	        TAcquisti acquisti = acquistiEJB.findById(acquistiId);
	        if (acquisti == null) {
	            response.setErrorCode(-1);
	            response.setErrorMessage("Acquisto con ID [" + acquistiId + "] non esiste");
	            return Response.ok(response).build();
	        }

	        AcquistiBean acquistiBean = new AcquistiBean();
	        BeanUtils.copyProperties(acquistiBean, acquisti);
	        
	        TUser utente = userEJB.findById(acquisti.getExtUserId());
	        if (utente != null) {
	            UserBean userBean = new UserBean();
	            BeanUtils.copyProperties(userBean, utente);
	 

	            acquistiBean.setUserBean(userBean);
	        }
	        
	       
	        TAuto auto = automezziEJB.findByAutoId(acquisti.getExtAutoId());
	        if (auto != null) {
	            AutoBean autoBean = new AutoBean();
	            BeanUtils.copyProperties(autoBean, auto);
	           
	            TMezzo mezzo = automezziEJB.findMezzoByAutoId(auto.getAutoId());
	            if (mezzo != null) {
	                MezzoBean mezzoBean = new MezzoBean();
	                BeanUtils.copyProperties(mezzoBean, mezzo);
	                autoBean.setMezzoBean(mezzoBean);
	                
	                acquistiBean.setSpesaTotale(mezzo.getPrezzo());
	            }

	            acquistiBean.setAutoBean(autoBean);
	        }
	        
	        
	        TMoto moto = automezziEJB.findByMotoId(acquisti.getExtMotoId());
	        if (moto != null) {
	            MotoBean motoBean = new MotoBean();
	            BeanUtils.copyProperties(motoBean, moto);
         
	            TMezzo mezzo = automezziEJB.findMezzoByMotoId(moto.getMotoId());
	            if (mezzo != null) {
	                MezzoBean mezzoBean = new MezzoBean();
	                BeanUtils.copyProperties(mezzoBean, mezzo);
	                motoBean.setMezzoBean(mezzoBean);
	                acquistiBean.setSpesaTotale(mezzo.getPrezzo());
	            }

	            acquistiBean.setMotoBean(motoBean);
	        }
	        
	        
	        TTir tir = automezziEJB.findByTirId(acquisti.getExtTirId());
	        if (tir != null) {
	            TirBean tirBean = new TirBean();
	            BeanUtils.copyProperties(tirBean, tir);
	      
	            TMezzo mezzo = automezziEJB.findMezzoByTirId(tir.getTirId());
	            if (mezzo != null) {
	                MezzoBean mezzoBean = new MezzoBean();
	                BeanUtils.copyProperties(mezzoBean, mezzo);
	                tirBean.setMezzoBean(mezzoBean);  
	                
	                acquistiBean.setSpesaTotale(mezzo.getPrezzo());
	            }

	            acquistiBean.setTirBean(tirBean);
	        }
	        
	        response.setAcquistiBean(acquistiBean);
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
	@Path("/findAllAcquisti")
	public Response findAllAcquisti() {
	    AcquistiResponse response = new AcquistiResponse();

	    try {
	        List<AcquistiBean> acquistiBeanList = acquistiEJB.findAll().stream().map(acquisto -> {
	            AcquistiBean bean = new AcquistiBean();
	            try {
	                BeanUtils.copyProperties(bean, acquisto);

	                // UTENTE
	                TUser utente = userEJB.findById(acquisto.getExtUserId());
	                if (utente != null) {
	                    UserBean userBean = new UserBean();
	                    BeanUtils.copyProperties(userBean, utente);
	                    bean.setUserBean(userBean);
	                }

	                // AUTO
	                TAuto auto = automezziEJB.findByAutoId(acquisto.getExtAutoId());
	                if (auto != null) {
	                    AutoBean autoBean = new AutoBean();
	                    BeanUtils.copyProperties(autoBean, auto);
	                    TMezzo mezzo = automezziEJB.findMezzoByAutoId(auto.getAutoId());
	                    if (mezzo != null) {
	                        MezzoBean mezzoBean = new MezzoBean();
	                        BeanUtils.copyProperties(mezzoBean, mezzo);
	                        autoBean.setMezzoBean(mezzoBean);
	                        
	                        bean.setSpesaTotale(mezzo.getPrezzo());
	                    }
	                    bean.setAutoBean(autoBean);
	                }

	                // MOTO
	                TMoto moto = automezziEJB.findByMotoId(acquisto.getExtMotoId());
	                if (moto != null) {
	                    MotoBean motoBean = new MotoBean();
	                    BeanUtils.copyProperties(motoBean, moto);
	                    TMezzo mezzo = automezziEJB.findMezzoByMotoId(moto.getMotoId());
	                    if (mezzo != null) {
	                        MezzoBean mezzoBean = new MezzoBean();
	                        BeanUtils.copyProperties(mezzoBean, mezzo);
	                        motoBean.setMezzoBean(mezzoBean);
	                        bean.setSpesaTotale(mezzo.getPrezzo());
	                    }
	                    bean.setMotoBean(motoBean);
	                }

	                // TIR
	                TTir tir = automezziEJB.findByTirId(acquisto.getExtTirId());
	                if (tir != null) {
	                    TirBean tirBean = new TirBean();
	                    BeanUtils.copyProperties(tirBean, tir);
	                    TMezzo mezzo = automezziEJB.findMezzoByTirId(tir.getTirId());
	                    if (mezzo != null) {
	                        MezzoBean mezzoBean = new MezzoBean();
	                        BeanUtils.copyProperties(mezzoBean, mezzo);
	                        tirBean.setMezzoBean(mezzoBean);
	                        bean.setSpesaTotale(mezzo.getPrezzo());
	                    }
	                    bean.setTirBean(tirBean);
	                }

	            } catch (Exception e) {
	                e.printStackTrace();
	            }

	            return bean;
	        }).collect(Collectors.toList());

	        response.setAcquistiBeanList(acquistiBeanList);
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
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/findAcquistiByUserId")
	public Response findAcquistiByUserId() {
	    AcquistiResponse response = new AcquistiResponse();
	    try {
	        String token = httpRequest.getHeader("Authorization");

	        if (token == null || !token.startsWith("Bearer ")) {
	            response.setErrorCode(401);
	            response.setErrorMessage("Token non presente");
	            return Response.ok(response).build();
	        }

	        String jwt = token.substring(7);
	        Integer userId = Integer.parseInt(JwtUtil.validateToken(jwt)); // Recupero userId dal token

	        List<TAcquisti> lista = acquistiEJB.findAcquistiByUserId(userId);
	        List<AcquistiBean> beanList = new ArrayList<>();
	        Double totaleSpese = 0.0;

	        for (TAcquisti acquisto : lista) {
	            AcquistiBean bean = new AcquistiBean();
	            BeanUtils.copyProperties(bean, acquisto);

	            TMezzo mezzo = null;

	            if (acquisto.getExtAutoId() != null) {
	                TAuto auto = automezziEJB.findByAutoId(acquisto.getExtAutoId());
	                AutoBean autoBean = new AutoBean();
	                BeanUtils.copyProperties(autoBean, auto);

	                mezzo = automezziEJB.findMezzoByAutoId(auto.getAutoId());
	                if (mezzo != null) {
	                    MezzoBean mezzoBean = new MezzoBean();
	                    BeanUtils.copyProperties(mezzoBean, mezzo);
	                    autoBean.setMezzoBean(mezzoBean);
	                    totaleSpese += mezzo.getPrezzo();
	                }

	                bean.setAutoBean(autoBean);
	            }

	            if (acquisto.getExtMotoId() != null) {
	                TMoto moto = automezziEJB.findByMotoId(acquisto.getExtMotoId());
	                MotoBean motoBean = new MotoBean();
	                BeanUtils.copyProperties(motoBean, moto);

	                mezzo = automezziEJB.findMezzoByMotoId(moto.getMotoId());
	                if (mezzo != null) {
	                    MezzoBean mezzoBean = new MezzoBean();
	                    BeanUtils.copyProperties(mezzoBean, mezzo);
	                    motoBean.setMezzoBean(mezzoBean);
	                    totaleSpese += mezzo.getPrezzo();
	                }

	                bean.setMotoBean(motoBean);
	            }

	            if (acquisto.getExtTirId() != null) {
	                TTir tir = automezziEJB.findByTirId(acquisto.getExtTirId());
	                TirBean tirBean = new TirBean();
	                BeanUtils.copyProperties(tirBean, tir);

	                mezzo = automezziEJB.findMezzoByTirId(tir.getTirId());
	                if (mezzo != null) {
	                    MezzoBean mezzoBean = new MezzoBean();
	                    BeanUtils.copyProperties(mezzoBean, mezzo);
	                    tirBean.setMezzoBean(mezzoBean);
	                    totaleSpese += mezzo.getPrezzo();
	                }

	                bean.setTirBean(tirBean);
	            }

	            beanList.add(bean);
	        }

	        response.setAcquistiBeanList(beanList);
	        response.setSpesaTotale(totaleSpese);
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
