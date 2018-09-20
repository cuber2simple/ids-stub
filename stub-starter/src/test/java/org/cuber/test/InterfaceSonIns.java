package org.cuber.test;

import org.cuber.stub.util.RefUtils;

public class InterfaceSonIns extends InterfaceFatherIns{
    public static void main(String[] args) {
        InterfaceSonIns in = new InterfaceSonIns();
        ((InterfaceSonIns) in).setTarget("123");
        InterfaceFatherIns old = (InterfaceFatherIns) in;
        System.out.println(RefUtils.findFieldValue(in,"target",String.class));
    }
}
