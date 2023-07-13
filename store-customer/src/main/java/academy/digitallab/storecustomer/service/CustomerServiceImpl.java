package academy.digitallab.storecustomer.service;

import academy.digitallab.storecustomer.entity.Customer;
import academy.digitallab.storecustomer.entity.Region;
import academy.digitallab.storecustomer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public List<Customer> findCustomerAll() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> findCustomerByRegion(Region region) {
        return customerRepository.findByRegion(region);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        //Hacer idenpotente el método post(Validar que no esxista el recurso que se va a crear)
        Customer customerDB = customerRepository.findByNumberId(customer.getNumberId());
        if (customerDB != null) {
            return customerDB;
        }
        //En otro caso crearlo
        customer.setState("CREATED");
        customerDB = customerRepository.save(customer);
        return customerDB;
    }
    @Override
    public Customer updateCustomer(Customer customer) {
        //Customer customerDB = customerRepository.findByNumberId(customer.getNumberId());
        Customer customerDB = getCustomer(customer.getId());
        if(null==customerDB){
            return null;
        }
        customerDB.setFirstName(customer.getFirstName());
        customerDB.setLastName(customer.getLastName());
        customerDB.setEmail((customer.getEmail()));
        customerDB.setPhotoUrl(customer.getPhotoUrl());
        return customerRepository.save(customerDB);
    }

    @Override
    public Customer deleteCustomer(Customer customer) {
        Customer customerDB = getCustomer(customer.getId());
        if(null==customerDB){
            return null;
        }
        //Se hace este proceso ya que los clientes no se deben borrar de la bd
        customerDB.setState("DELETED");
        customerDB = customerRepository.save(customerDB);
        return customerDB;
    }

    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).orElse(null);
    }
}
