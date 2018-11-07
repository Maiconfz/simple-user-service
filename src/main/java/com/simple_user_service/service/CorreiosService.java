package com.simple_user_service.service;

import org.springframework.stereotype.Service;

import com.example.generated.AtendeClienteService;
import com.example.generated.EnderecoERP;
import com.example.generated.SQLException_Exception;
import com.example.generated.SigepClienteException;

@Service
public class CorreiosService {
	private final AtendeClienteService atendeClienteService;

	public CorreiosService() {
		this.atendeClienteService = new AtendeClienteService();
	}

	public EnderecoERP consultaCEP(String cep) throws SQLException_Exception, SigepClienteException {
		return this.atendeClienteService.getAtendeClientePort().consultaCEP(cep);
	}

}
