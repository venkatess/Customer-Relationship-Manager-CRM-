package com.luv2code.springdemo.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springdemo.dao.CustomerDAO;
import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;
import com.luv2code.springdemo.service.CustomerServiceImpl;


@Controller
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	@GetMapping("/list")
	public String listCustomers(Model theModel) {
		List<Customer> customers = customerService.getCustomers();
		theModel.addAttribute("customers", customers);
		
		return "list-customers";
	}
	
	@GetMapping("/showFormAdd")
	public String showFormAdd(Model theModel){
		
		//Create Model attribute to bind form data
		
		Customer theCustomer=new Customer();
		theModel.addAttribute("customer", theCustomer);
		return "customer-forms";
	}
	
	@PostMapping("/saveCustomer")
	//@ModelAttribute used to get the model from the html form and then bind to the POJO we have 
	public String saveCustomer(@ModelAttribute("customer") Customer theCustomer){
		System.out.println("Inside save Customer");
		customerService.saveCustomer(theCustomer);
		return "redirect:/customer/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int theId, Model theModel){
		//get the customer from db
		Customer theCustomer = customerService.getCustomer(theId);
		//Set the customer as a model attribute to pre-populate the form
		theModel.addAttribute("customer",theCustomer );
		//Send over to our form
		return "customer-forms";
	}
	
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int theId){
		
		//Delete the Customer
		
		customerService.deleteCustomer(theId);
		return "redirect:/customer/list";
	}
	
	@PostMapping("/search")
	public String searchCustomer(@RequestParam("theSearchName") String theName, Model theModel){
		//Search customers from service
		
		List<Customer> theCustomers=customerService.searchCustomers(theName);
		
		theModel.addAttribute("customers",theCustomers);
		return "list-customers";
	}
	
}




