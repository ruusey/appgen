package com.appgen.rest;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appgen.models.Transaction;

@RestController
public class SpringRabbitMQController {

	@Produce(uri = "direct:rest-json-input")
	private ProducerTemplate template;

	@RequestMapping(value = "/transaction", method = RequestMethod.GET)
	public String postTransaction(@RequestParam int id, @RequestParam String employeeId, @RequestParam double total) {

		Transaction tx = new Transaction();
		tx.setId(id);
		tx.setEmployeeId(employeeId);
		tx.setTotal(total);
		template.asyncSendBody(template.getDefaultEndpoint(), tx);
		return "";
	}
}