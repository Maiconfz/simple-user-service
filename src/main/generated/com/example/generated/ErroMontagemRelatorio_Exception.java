
package com.example.generated;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.2.7
 * 2018-11-06T23:40:47.045-02:00
 * Generated source version: 3.2.7
 */

@WebFault(name = "ErroMontagemRelatorio", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/")
public class ErroMontagemRelatorio_Exception extends java.lang.Exception {

    private com.example.generated.ErroMontagemRelatorio erroMontagemRelatorio;

    public ErroMontagemRelatorio_Exception() {
        super();
    }

    public ErroMontagemRelatorio_Exception(String message) {
        super(message);
    }

    public ErroMontagemRelatorio_Exception(String message, java.lang.Throwable cause) {
        super(message, cause);
    }

    public ErroMontagemRelatorio_Exception(String message, com.example.generated.ErroMontagemRelatorio erroMontagemRelatorio) {
        super(message);
        this.erroMontagemRelatorio = erroMontagemRelatorio;
    }

    public ErroMontagemRelatorio_Exception(String message, com.example.generated.ErroMontagemRelatorio erroMontagemRelatorio, java.lang.Throwable cause) {
        super(message, cause);
        this.erroMontagemRelatorio = erroMontagemRelatorio;
    }

    public com.example.generated.ErroMontagemRelatorio getFaultInfo() {
        return this.erroMontagemRelatorio;
    }
}