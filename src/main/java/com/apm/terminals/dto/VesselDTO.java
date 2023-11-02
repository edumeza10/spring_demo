package com.apm.terminals.dto;

public class VesselDTO {
    protected String vesselName;
    protected String vesselInboundId;
    protected String vesselOutboundId;
    protected String vesselETA;
    protected String vesselETD;
    protected String vesselStartReceptionDry;
    protected String vesselCutOff;
    protected String vesselReeferCutOff;
    protected String vesselDueDateT1;
    protected String vesselStatus;

    public VesselDTO() {
    }

    public String getVesselName() {
        return this.vesselName;
    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }

    public String getVesselInboundId() {
        return this.vesselInboundId;
    }

    public void setVesselInboundId(String vesselInboundId) {
        this.vesselInboundId = vesselInboundId;
    }

    public String getVesselOutboundId() {
        return this.vesselOutboundId;
    }

    public void setVesselOutboundId(String vesselOutboundId) {
        this.vesselOutboundId = vesselOutboundId;
    }

    public String getVesselETA() {
        return this.vesselETA;
    }

    public void setVesselETA(String vesselETA) {
        this.vesselETA = vesselETA;
    }

    public String getVesselETD() {
        return this.vesselETD;
    }

    public void setVesselETD(String vesselETD) {
        this.vesselETD = vesselETD;
    }

    public String getVesselStartReceptionDry() {
        return this.vesselStartReceptionDry;
    }

    public void setVesselStartReceptionDry(String vesselStartReceptionDry) {
        this.vesselStartReceptionDry = vesselStartReceptionDry;
    }

    public String getVesselCutOff() {
        return this.vesselCutOff;
    }

    public void setVesselCutOff(String vesselCutOff) {
        this.vesselCutOff = vesselCutOff;
    }

    public String getVesselReeferCutOff() {
        return this.vesselReeferCutOff;
    }

    public void setVesselReeferCutOff(String vesselReeferCutOff) {
        this.vesselReeferCutOff = vesselReeferCutOff;
    }

    public String getVesselDueDateT1() {
        return this.vesselDueDateT1;
    }

    public void setVesselDueDateT1(String vesselDueDateT1) {
        this.vesselDueDateT1 = vesselDueDateT1;
    }

    public String getVesselStatus() {
        return this.vesselStatus;
    }

    public void setVesselStatus(String vesselStatus) {
        this.vesselStatus = vesselStatus;
    }
}