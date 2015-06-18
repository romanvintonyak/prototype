package com.epam.controllers;

import com.epam.customer.data.EpamAddressData;
import com.epam.customer.data.EpamCustomerData;
import com.epam.customer.facades.EpamCustomerFacade;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;

/**
 * @author Roman_Kovalenko
 * @author Irina_Vasilyeva
 */
@Controller
@RequestMapping("/v1/customers")
public class CustomerController {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);

    private EpamCustomerFacade epamCustomerFacade;

    @Autowired
    public CustomerController(EpamCustomerFacade epamCustomerFacade) {
        this.epamCustomerFacade = epamCustomerFacade;
    }

    @RequestMapping(value = "/{customerId}/address", method = RequestMethod.GET)
    @ResponseBody
    public Collection<EpamAddressData> getCustomerAddresses(@PathVariable("customerId") String customerId) {
        LOG.info(String.format("Invoke the #findCustomerAddresses() with parameter customerId=%s.", customerId));
        return epamCustomerFacade.findCustomerAddresses(decodeBase64(customerId));
    }

    private String decodeBase64(@PathVariable("customerId") String customerId) {
        return new String(Base64.getDecoder().decode(customerId.getBytes(StandardCharsets.UTF_8)));
    }

    @RequestMapping(value = "/{customerId}/address", method = RequestMethod.POST)
    @ResponseBody
    public EpamAddressData createCustomerAddresses(@PathVariable("customerId") String customerId, @RequestBody EpamAddressData addressData) {
        LOG.info(String.format("Invoke the #createCustomerAddress() with parameters: addressData=%s, customerId=%s.",
                addressData, decodeBase64(customerId)));
        return epamCustomerFacade.createCustomerAddress(addressData, decodeBase64(customerId));
    }

    @RequestMapping(value = "/{customerId}/address", method = RequestMethod.PUT)
    @ResponseBody
    public EpamAddressData updateCustomerAddresses(@PathVariable("customerId") String customerId, @RequestBody EpamAddressData addressData) {
        LOG.info(String.format("Invoke the #updateCustomerAddress() with parameters: addressData=%s, customerId=%s.",
                addressData, decodeBase64(customerId)));
        return epamCustomerFacade.updateCustomerAddress(addressData, decodeBase64(customerId));
    }

    @RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
    @ResponseBody
    public EpamCustomerData getCustomer(@PathVariable("customerId") String customerId) {
        LOG.info(String.format("Invoke the #findCustomerByUID() with parameter customerId=%s.", decodeBase64(customerId)));
        return epamCustomerFacade.findCustomerByUID(decodeBase64(customerId));
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void createCustomer(@RequestBody EpamCustomerData customerData) throws DuplicateUidException {
        LOG.info(String.format("Invoke the #createCustomer() with parameter customerData=%s.", customerData));
        epamCustomerFacade.createCustomer(customerData);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateCustomer(@RequestBody EpamCustomerData customerData) {
        LOG.info(String.format("Invoke the #updateCustomer() with parameter customerData=%s.", customerData));
        epamCustomerFacade.updateCustomer(customerData);
    }
}
