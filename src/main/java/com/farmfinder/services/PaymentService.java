package com.farmfinder.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.farmfinder.commands.CategoryRepo;
import com.farmfinder.commands.PaymentRepo;
import com.farmfinder.config.MongoConfig;
import com.farmfinder.metadata.Metadata;
import com.farmfinder.model.Category;
import com.farmfinder.model.Payment;
import com.sun.xml.bind.v2.model.core.ID;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@Api(value = "/payment", description = "Main Payment REST service for FarmFinder")
@Produces({"application/json"})
@Path("/payment")
public class PaymentService {
	@POST
	@Path("/createPayment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Creates a payment from data and stores in database", notes = "Created by: Rishiban Ramesh", response=Payment.class)
	@ApiResponses(value = {
			  @ApiResponse(code = 201, message = "Able to store new payment class in database")
			  })	
	public Response createPayment(@ApiParam(value="The payment JSON data", required= true)String data){

		/* We are getting application context and repo here*/
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
		PaymentRepo repo = (PaymentRepo) ctx.getBean(PaymentRepo.class) ;
		/* here we convert Payment class to match JSON*/
		ObjectMapper map = new ObjectMapper();

		try { 
			Payment p = map.readValue(data, Payment.class) ;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		try{ 
			Payment p = map.readValue(data, Payment.class) ;
		}catch (Exception e1) {
			e1.printStackTrace();
		}
		
		/*Create new payment class*/ 
		Payment pay = new Payment() ;
		pay.setName("Buy your products") ;		
		repo.save(pay) ;
		return Response.status(201).entity(pay).build();
		}
	 
	@GET
	@Path("/geAllPayment")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all payments", notes = "Created by: Rishiban Ramesh", response=Payment.class)
	@ApiResponses(value = {
			  @ApiResponse(code = 200, message = "Able to get all payments")
			  })	
	public Response getAllPayment() {
		/* We are getting application context and repo here*/
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
		PaymentRepo repo = (PaymentRepo) ctx.getBean(PaymentRepo.class) ;
		List<Payment> getpay = repo.findAll();
		return Response.status(200).entity(getpay).build();
	}
	/*rest service for find all payments*/
	@GET
	@Path("/findID/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get payment by id", notes = "Created by: Rishiban Ramesh", response=Payment.class)
	@ApiResponses(value = {
			  @ApiResponse(code = 200, message = "Able to find payment")
			  })	
	public Response findId(@ApiParam(value="The id of the payment")@PathParam("id")String id) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
		PaymentRepo repo = (PaymentRepo) ctx.getBean(PaymentRepo.class) ;
		repo.findOne(id);
		return Response.status(200).entity(id).build();
	}
	
	/*rest service for delete payment*/
	@DELETE
	@Path("/deletePayment/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Delete a payment with id", notes = "Created by: Rishiban Ramesh", response=Payment.class)
	@ApiResponses(value = {
			  @ApiResponse(code = 200, message = "Able to delete payment")
			  })	
	public Response deleteid(@PathParam("id")String id) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
		PaymentRepo repo = (PaymentRepo) ctx.getBean(PaymentRepo.class) ;
		repo.delete(id);
		return Response.status(200).entity(id).build();
	}
}
	
	

	
	
	
	
	


