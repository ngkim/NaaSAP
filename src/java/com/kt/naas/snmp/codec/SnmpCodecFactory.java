package com.kt.naas.snmp.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class SnmpCodecFactory implements ProtocolCodecFactory{
	private ProtocolEncoder encoder;
    private ProtocolDecoder decoder;
    
    public SnmpCodecFactory() {
        encoder = null;
        decoder = new SnmpMessageDecoder();
    }
    
    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return encoder;
    }

    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return decoder;
    }
}
