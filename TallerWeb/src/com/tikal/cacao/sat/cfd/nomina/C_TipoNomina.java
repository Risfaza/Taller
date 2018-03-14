//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.03.01 at 05:42:21 PM CST 
//


package com.tikal.cacao.sat.cfd.nomina;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for c_TipoNomina.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="c_TipoNomina">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;whiteSpace value="collapse"/>
 *     &lt;enumeration value="O"/>
 *     &lt;enumeration value="E"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "c_TipoNomina", namespace = "http://www.sat.gob.mx/sitio_internet/cfd/catalogos/Nomina")
@XmlEnum
public enum C_TipoNomina {

	/** N&oacute;mina ordinaria */
    O,
    /** N&oacute;mina extraordinaria */
    E;

    public String value() {
        return name();
    }

	public String getDescripcion() {
    	switch (this) {
	    	case O:
	    		return "ORDINARIA";
	    	case E:
	    		return "EXTRAORDINARIA";
	    	default :
	    		return "";
    	}
    }
	
    public static C_TipoNomina fromValue(String v) {
        return valueOf(v);
    }

}
