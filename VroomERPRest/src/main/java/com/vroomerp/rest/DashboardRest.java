package com.vroomerp.rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.beanutils.BeanUtils;

import com.vroomerp.common.dto.auto.AutoBean;
import com.vroomerp.common.dto.auto.AutoResponse;
import com.vroomerp.common.dto.dashboard.DashboardResponse;
import com.vroomerp.common.dto.moto.MotoBean;
import com.vroomerp.common.dto.tir.TirBean;
import com.vroomerp.ejb.AutomezziEJBInterface;
import com.vroomerp.ejb.UserEJBInterface;
import com.vroomerp.model.TAuto;
import com.vroomerp.model.TMoto;
import com.vroomerp.model.TTir;
import com.vroomerp.security.JWTTokenNeeded;

@Stateless
@Path("dashboard")
public class DashboardRest {
	
	@EJB
	UserEJBInterface userEJB;

	@EJB
	AutomezziEJBInterface automezziEJB;

	@Context
	HttpServletRequest httpRequest;

	
	
	@GET
	@Path("/auto/search")
	@Produces(MediaType.APPLICATION_JSON)
	@JWTTokenNeeded
	public Response searchAuto(
	    @QueryParam("marca") String marca,
	    @QueryParam("modello") String modello,
	    @QueryParam("targa") String targa,
	    @QueryParam("limit") Integer limit
	) {
	    try {
	        List<TAuto> autos = automezziEJB.findAllAutoFiltered(marca, modello, targa, limit);
	        List<AutoBean> results = autos.stream().map(auto -> {
	            AutoBean bean = new AutoBean();
	            try {
	                BeanUtils.copyProperties(bean, auto);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            return bean;
	        }).collect(Collectors.toList());

	        DashboardResponse response = new DashboardResponse();
	        response.setAutoList(results);
	        response.setErrorCode(0);
	        response.setErrorMessage("OK");

	        return Response.ok(response).build();

	    } catch (Exception e) {
	        e.printStackTrace();
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	                .entity("Errore durante la ricerca").build();
	    }
	}

	@GET
	@Path("/moto/search")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@JWTTokenNeeded
	public Response searchMoto(
	    @QueryParam("marca") String marca,
	    @QueryParam("modello") String modello,
	    @QueryParam("targa") String targa,
	    @QueryParam("limit") Integer limit
	) {
	    DashboardResponse response = new DashboardResponse();
	    try {
	        List<TMoto> lista = automezziEJB.findAllMotoFiltered(marca, modello, targa, 4);
	        
	        List<MotoBean> beans = lista.stream().map(moto -> {
	            MotoBean bean = new MotoBean();
	            try {
	                BeanUtils.copyProperties(bean, moto);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            return bean;
	        }).collect(Collectors.toList());

	        response.setMotoList(beans);
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


	@GET
	@Path("/tir/search")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@JWTTokenNeeded
	public Response searchTir(
	    @QueryParam("marca") String marca,
	    @QueryParam("modello") String modello,
	    @QueryParam("targa") String targa,
	    @QueryParam("limit") Integer limit
	) {
	    DashboardResponse response = new DashboardResponse();
	    try {
	        List<TTir> lista = automezziEJB.findAllTirFiltered(marca, modello, targa, 4);
	        
	        List<TirBean> beans = lista.stream().map(tir -> {
	            TirBean bean = new TirBean();
	            try {
	                BeanUtils.copyProperties(bean, tir);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            return bean;
	        }).collect(Collectors.toList());

	        response.setTirList(beans);
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
