package com.epam.controllers;

import com.epam.customer.data.EpamAddressData;
import com.epam.customer.data.EpamCustomerData;
import com.epam.customer.facades.EpamCustomerFacade;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Collection;

/**
 * @author Roman_Kovalenko
 * @author Irina_Vasilyeva
 */
@Controller
@RequestMapping("/v1/customers")
public class CustomerController {

    private EpamCustomerFacade epamCustomerFacade;

    @Autowired
    public CustomerController(EpamCustomerFacade epamCustomerFacade) {
        this.epamCustomerFacade = epamCustomerFacade;
    }

    @RequestMapping(value = "/{customerId}/address", method = RequestMethod.GET)
    @ResponseBody
    public Collection<EpamAddressData> getCustomerAddresses(@PathVariable("customerId") String customerId) {
        return epamCustomerFacade.findCustomerAddresses(decodeBase64(customerId));
    }

    private String decodeBase64(@PathVariable("customerId") String customerId) {
        return new String(Base64.decodeBase64((customerId.getBytes(StandardCharsets.UTF_8))));
    }

    @RequestMapping(value = "/{customerId}/address", method = RequestMethod.POST)
    @ResponseBody
    public EpamAddressData createCustomerAddresses(@PathVariable("customerId") String customerId, @RequestBody EpamAddressData addressData) {
        return epamCustomerFacade.createCustomerAddress(addressData, decodeBase64(customerId));
    }

    @RequestMapping(value = "/{customerId}/address", method = RequestMethod.PUT)
    @ResponseBody
    public EpamAddressData updateCustomerAddresses(@PathVariable("customerId") String customerId, @RequestBody EpamAddressData addressData) {
        return epamCustomerFacade.updateCustomerAddress(addressData, decodeBase64(customerId));
    }

    @RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
    @ResponseBody
    public EpamCustomerData getCustomer(@PathVariable("customerId") String customerId) {
        return epamCustomerFacade.findCustomerByUID(decodeBase64(customerId));
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void createCustomer(@RequestBody EpamCustomerData customerData) throws DuplicateUidException {
        epamCustomerFacade.createCustomer(customerData);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateCustomer(@RequestBody EpamCustomerData customerData) {
        epamCustomerFacade.updateCustomer(customerData);
    }
}
