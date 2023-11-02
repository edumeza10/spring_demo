package com.apm.terminals.util;

public class VesselStatusUtil {
    public VesselStatusUtil() {
    }

    public static String getLabelByStatus(String visitStatus) {
        switch (visitStatus.hashCode()) {
            case -1288399483:
                if (!visitStatus.equals("70CLOSED")) {
                    return "EN VIAJE";
                }

                return "FINALIZADO";
            case -1167145907:
                if (!visitStatus.equals("60DEPARTED")) {
                    return "EN VIAJE";
                }

                return "FINALIZADO";
            case 48428880:
                if (!visitStatus.equals("30ARRIVED")) {
                    return "EN VIAJE";
                }
                break;
            case 236863323:
                if (visitStatus.equals("20INBOUND")) {
                    return "EN VIAJE";
                }

                return "EN VIAJE";
            case 500346133:
                if (!visitStatus.equals("40WORKING")) {
                    return "EN VIAJE";
                }
                break;
            case 1131131849:
                if (visitStatus.equals("10CREATED")) {
                    return "EN VIAJE";
                }

                return "EN VIAJE";
            default:
                return "EN VIAJE";
        }

        return "OPERANDO";
    }
}