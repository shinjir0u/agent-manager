package agentmanager.saleexecutive.rest;

import java.net.URI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import agentmanager.saleexecutive.service.SaleExecutiveService;

@RestController
@RequestMapping("/sale_executives")
public class SaleExecutiveController {

	private final Logger logger = LogManager.getLogger(SaleExecutiveController.class);

	private SaleExecutiveService saleExecutiveService;

	public SaleExecutiveController(SaleExecutiveService saleExecutiveService) {
		this.saleExecutiveService = saleExecutiveService;
	}

	private URI generateEntryUri(Object entryId) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(entryId).toUri();
		return uri;
	}

}
