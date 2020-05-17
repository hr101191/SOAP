/**
 * CalculatorMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.9  Built on : Nov 16, 2018 (12:05:37 GMT)
 */
package com.hurui.calculatorservice;


/**
 *  CalculatorMessageReceiverInOut message receiver
 */
public class CalculatorMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutMessageReceiver {
    public void invokeBusinessLogic(
        org.apache.axis2.context.MessageContext msgContext,
        org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault {
        try {
            // get the implementation class for the Web Service
            Object obj = getTheImplementationObject(msgContext);

            CalculatorSkeleton skel = (CalculatorSkeleton) obj;

            //Out Envelop
            org.apache.axiom.soap.SOAPEnvelope envelope = null;

            //Find the axisOperation that has been set by the Dispatch phase.
            org.apache.axis2.description.AxisOperation op = msgContext.getOperationContext()
                                                                      .getAxisOperation();

            if (op == null) {
                throw new org.apache.axis2.AxisFault(
                    "Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
            }

            java.lang.String methodName;

            if ((op.getName() != null) &&
                    ((methodName = org.apache.axis2.util.JavaUtils.xmlNameToJavaIdentifier(
                            op.getName().getLocalPart())) != null)) {
                if ("multiply".equals(methodName)) {
                    com.hurui.calculatorservice.MultiplyResponse multiplyResponse17 =
                        null;
                    com.hurui.calculatorservice.Multiply wrappedParam = (com.hurui.calculatorservice.Multiply) fromOM(msgContext.getEnvelope()
                                                                                                                                .getBody()
                                                                                                                                .getFirstElement(),
                            com.hurui.calculatorservice.Multiply.class);

                    multiplyResponse17 = skel.multiply(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            multiplyResponse17, false,
                            new javax.xml.namespace.QName(
                                "http://CalculatorService.hurui.com/",
                                "MultiplyResponse"));
                } else
                 if ("divide".equals(methodName)) {
                    com.hurui.calculatorservice.DivideResponse divideResponse19 = null;
                    com.hurui.calculatorservice.Divide wrappedParam = (com.hurui.calculatorservice.Divide) fromOM(msgContext.getEnvelope()
                                                                                                                            .getBody()
                                                                                                                            .getFirstElement(),
                            com.hurui.calculatorservice.Divide.class);

                    divideResponse19 = skel.divide(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            divideResponse19, false,
                            new javax.xml.namespace.QName(
                                "http://CalculatorService.hurui.com/",
                                "DivideResponse"));
                } else
                 if ("add".equals(methodName)) {
                    com.hurui.calculatorservice.AddResponse addResponse21 = null;
                    com.hurui.calculatorservice.Add wrappedParam = (com.hurui.calculatorservice.Add) fromOM(msgContext.getEnvelope()
                                                                                                                      .getBody()
                                                                                                                      .getFirstElement(),
                            com.hurui.calculatorservice.Add.class);

                    addResponse21 = skel.add(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            addResponse21, false,
                            new javax.xml.namespace.QName(
                                "http://CalculatorService.hurui.com/",
                                "AddResponse"));
                } else
                 if ("subtract".equals(methodName)) {
                    com.hurui.calculatorservice.SubtractResponse subtractResponse23 =
                        null;
                    com.hurui.calculatorservice.Subtract wrappedParam = (com.hurui.calculatorservice.Subtract) fromOM(msgContext.getEnvelope()
                                                                                                                                .getBody()
                                                                                                                                .getFirstElement(),
                            com.hurui.calculatorservice.Subtract.class);

                    subtractResponse23 = skel.subtract(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            subtractResponse23, false,
                            new javax.xml.namespace.QName(
                                "http://CalculatorService.hurui.com/",
                                "SubtractResponse"));
                } else {
                    throw new java.lang.RuntimeException("method not found");
                }

                newMsgContext.setEnvelope(envelope);
            }
        } catch (java.lang.Exception e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    //
    private org.apache.axiom.om.OMElement toOM(
        com.hurui.calculatorservice.Multiply param, boolean optimizeContent)
        throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.hurui.calculatorservice.Multiply.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.hurui.calculatorservice.MultiplyResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.hurui.calculatorservice.MultiplyResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.hurui.calculatorservice.Divide param, boolean optimizeContent)
        throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.hurui.calculatorservice.Divide.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.hurui.calculatorservice.DivideResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.hurui.calculatorservice.DivideResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.hurui.calculatorservice.Add param, boolean optimizeContent)
        throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.hurui.calculatorservice.Add.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.hurui.calculatorservice.AddResponse param, boolean optimizeContent)
        throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.hurui.calculatorservice.AddResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.hurui.calculatorservice.Subtract param, boolean optimizeContent)
        throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.hurui.calculatorservice.Subtract.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.hurui.calculatorservice.SubtractResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.hurui.calculatorservice.SubtractResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.hurui.calculatorservice.MultiplyResponse param,
        boolean optimizeContent, javax.xml.namespace.QName elementQName)
        throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    com.hurui.calculatorservice.MultiplyResponse.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private com.hurui.calculatorservice.MultiplyResponse wrapMultiply() {
        com.hurui.calculatorservice.MultiplyResponse wrappedElement = new com.hurui.calculatorservice.MultiplyResponse();

        return wrappedElement;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.hurui.calculatorservice.DivideResponse param,
        boolean optimizeContent, javax.xml.namespace.QName elementQName)
        throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    com.hurui.calculatorservice.DivideResponse.MY_QNAME, factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private com.hurui.calculatorservice.DivideResponse wrapDivide() {
        com.hurui.calculatorservice.DivideResponse wrappedElement = new com.hurui.calculatorservice.DivideResponse();

        return wrappedElement;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.hurui.calculatorservice.AddResponse param, boolean optimizeContent,
        javax.xml.namespace.QName elementQName)
        throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    com.hurui.calculatorservice.AddResponse.MY_QNAME, factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private com.hurui.calculatorservice.AddResponse wrapAdd() {
        com.hurui.calculatorservice.AddResponse wrappedElement = new com.hurui.calculatorservice.AddResponse();

        return wrappedElement;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.hurui.calculatorservice.SubtractResponse param,
        boolean optimizeContent, javax.xml.namespace.QName elementQName)
        throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    com.hurui.calculatorservice.SubtractResponse.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private com.hurui.calculatorservice.SubtractResponse wrapSubtract() {
        com.hurui.calculatorservice.SubtractResponse wrappedElement = new com.hurui.calculatorservice.SubtractResponse();

        return wrappedElement;
    }

    /**
     *  get the default envelope
     */
    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory) {
        return factory.getDefaultEnvelope();
    }

    private java.lang.Object fromOM(org.apache.axiom.om.OMElement param,
        java.lang.Class type) throws org.apache.axis2.AxisFault {
        try {
            if (com.hurui.calculatorservice.Add.class.equals(type)) {
                return com.hurui.calculatorservice.Add.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.hurui.calculatorservice.AddResponse.class.equals(type)) {
                return com.hurui.calculatorservice.AddResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.hurui.calculatorservice.Divide.class.equals(type)) {
                return com.hurui.calculatorservice.Divide.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.hurui.calculatorservice.DivideResponse.class.equals(type)) {
                return com.hurui.calculatorservice.DivideResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.hurui.calculatorservice.Multiply.class.equals(type)) {
                return com.hurui.calculatorservice.Multiply.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.hurui.calculatorservice.MultiplyResponse.class.equals(type)) {
                return com.hurui.calculatorservice.MultiplyResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.hurui.calculatorservice.Subtract.class.equals(type)) {
                return com.hurui.calculatorservice.Subtract.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.hurui.calculatorservice.SubtractResponse.class.equals(type)) {
                return com.hurui.calculatorservice.SubtractResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }
        } catch (java.lang.Exception e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }

        return null;
    }

    private org.apache.axis2.AxisFault createAxisFault(java.lang.Exception e) {
        org.apache.axis2.AxisFault f;
        Throwable cause = e.getCause();

        if (cause != null) {
            f = new org.apache.axis2.AxisFault(e.getMessage(), cause);
        } else {
            f = new org.apache.axis2.AxisFault(e.getMessage());
        }

        return f;
    }
} //end of class
