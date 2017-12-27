package com.luv2code.springdemo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.entity.Customer;
@Repository
public class CustomerDAOImpl implements CustomerDAO {
//Need to Inject Hibernate session Factory to talk to DB
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override

	public List<Customer> getCustomers() {
		//get the current Hibernate Session
		Session currentSession=sessionFactory.getCurrentSession();
		
		//Create query
		Query<Customer> theQuery = currentSession.createQuery("from Customer order by lastName",Customer.class);
		
		
		//execute query and create the list
		List<Customer> customers=theQuery.getResultList();
		//return the results
		
		
		return customers;
	}


	@Override
	public void saveCustomer(Customer theCustomer) {
		// TODO Auto-generated method stub
		//get the hibernate Session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//create Query
		
		
		//Save/Update the customer
		currentSession.saveOrUpdate(theCustomer);
		
	}


	@Override
	public Customer getCustomers(int theId) {
		Session currentSession = sessionFactory.getCurrentSession();
		//retrieve from DB using the id 
		Customer theCustomer=currentSession.get(Customer.class,theId);
		return theCustomer;
	}


	@Override
	public void deleteCustomer(int theId) {
		// TODO Auto-generated method stub
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		//delete from Db using the Id
		
		Query theQuery = currentSession.createQuery("delete from Customer where id=:customerId");
		theQuery.setParameter("customerId",theId);
		theQuery.executeUpdate();
	}


	@Override
	public List<Customer> getCustomers(String theName) {
		

	        // get the current hibernate session
	        Session currentSession = sessionFactory.getCurrentSession();
	        
	        Query theQuery = null;
	        
	        //
	        // only search by name if theSearchName is not empty
	        //
	        if (theName != null && theName.trim().length() > 0) {

	            // search for firstName or lastName ... case insensitive
	            theQuery =currentSession.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName", Customer.class);
	            theQuery.setParameter("theName", "%" + theName.toLowerCase() + "%");

	        }
	        else {
	            // theSearchName is empty ... so just get all customers
	            theQuery =currentSession.createQuery("from Customer", Customer.class);            
	        }
	        
	        // execute query and get result list
	        List<Customer> customers = theQuery.getResultList();
	                
	        // return the results        
	        return customers;
	        
	    }
		
	}


