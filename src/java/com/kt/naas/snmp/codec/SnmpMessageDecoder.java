package com.kt.naas.snmp.codec;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.PDU;
import org.snmp4j.asn1.BER;
import org.snmp4j.asn1.BERInputStream;
import org.snmp4j.asn1.BER.MutableByte;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OctetString;

public class SnmpMessageDecoder extends ProtocolDecoderAdapter {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@SuppressWarnings("unused")
	@Override
	public void decode(IoSession session, IoBuffer buffer, ProtocolDecoderOutput output)
			throws Exception {
		ByteBuffer pduBuffer = buffer.buf();
        PDU pdu = new PDU();
        
        try {
        	BERInputStream berStream = new BERInputStream(pduBuffer);
        	MutableByte mutableByte = new MutableByte();
        	
        	int length = BER.decodeHeader(berStream, mutableByte);
        	int startPos = (int)berStream.getPosition();
        	
//        	logger.debug(String.format("length=%s, startPos=%s", length, startPos));

            if (mutableByte.getValue() != BER.SEQUENCE) {
                String txt = "SNMPv2c PDU must start with a SEQUENCE";
                throw new IOException(txt);
            }
            
            Integer32 version = new Integer32();
            version.decodeBER(berStream);

            // decode community string
            OctetString securityName = new OctetString();
            securityName.decodeBER(berStream);

            // decode the remaining PDU
            pdu.decodeBER(berStream);
           
            if (pdu.getType() != PDU.TRAP)
            {
            	logger.error("Message is not trap" + pdu);
            	return;
            }
            
            output.write(pdu);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
