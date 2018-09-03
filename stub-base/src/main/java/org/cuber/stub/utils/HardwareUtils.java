package org.cuber.stub.utils;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.cuber.stub.StubConstant;

import java.net.InetAddress;
import java.net.NetworkInterface;

public class HardwareUtils {

    public static String signature(InetAddress address) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        byte[] mac = NetworkInterface.getByInetAddress(address).getHardwareAddress();
        stringBuilder.append(address.toString())
                .append(StubConstant.PATH_SPLIT)
                .append(StringUtils.upperCase(Hex.encodeHexString(mac)));
        return stringBuilder.toString();
    }

    public static String signatureLocal() throws Exception {
        String signatureLocal = signature(InetAddress.getLocalHost());
        return StringUtils.replace(signatureLocal, StubConstant.PATH_SPLIT, StubConstant.NORMAL_SPLIT);
    }

}
