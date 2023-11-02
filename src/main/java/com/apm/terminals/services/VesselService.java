package com.apm.terminals.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.apm.terminals.dto.VesselDTO;
import com.apm.terminals.util.DateUtil;
import com.apm.terminals.util.VesselStatusUtil;

@Service
public class VesselService {
    @Value("${vessel.start.dry.less.days}")
    private int lessDays;
    @Value("${vessel.first.free.day}")
    private int firstFreeDay;
    @Autowired
    QueryService queryService;
    @Autowired
    NavisDatabaseConnectionService databaseConnection;

    public VesselService() {
    }

    public ArrayList<VesselDTO> getVessels() throws SQLException {
        String query = this.queryService.getVessels();
        ArrayList<VesselDTO> vsls = new ArrayList();
        Connection conn = this.databaseConnection.connectDatabase();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while(rs.next()) {
            VesselDTO vsl = new VesselDTO();
            vsl.setVesselName(rs.getString("vesselName"));
            vsl.setVesselInboundId(rs.getString("visitIn"));
            vsl.setVesselOutboundId(rs.getString("visitOut"));
            vsl.setVesselETA(DateUtil.parseDateTime(rs.getTimestamp("ETA")));
            vsl.setVesselETD(DateUtil.parseDateTime(rs.getTimestamp("ETD")));
            vsl.setVesselStartReceptionDry(rs.getTimestamp("startDry") != null ? DateUtil.parseDate(this.recalculateRecepctionDry(rs.getTimestamp("startDry"))) : null);
            vsl.setVesselCutOff(DateUtil.parseDateTime(rs.getTimestamp("cutOff")));
            vsl.setVesselReeferCutOff(DateUtil.parseDateTime(rs.getTimestamp("reeferCutoff")));
            vsl.setVesselDueDateT1(rs.getTimestamp("vencT1") != null ? DateUtil.parseDate(this.recalculateFirstFreeDay(rs.getTimestamp("vencT1"))) : null);
            vsl.setVesselStatus(VesselStatusUtil.getLabelByStatus(rs.getString("visitstatus")));
            vsls.add(vsl);
        }

        return vsls;
    }

    private Date recalculateRecepctionDry(Date startDry) {
        Calendar c = Calendar.getInstance();
        c.setTime(startDry);
        c.add(6, this.lessDays);
        return c.getTime();
    }

    private Date recalculateFirstFreeDay(Date ffd) {
        Calendar c = Calendar.getInstance();
        c.setTime(ffd);
        c.add(6, this.firstFreeDay);
        return c.getTime();
    }

    public ArrayList<VesselDTO> getBarges() throws SQLException {
        String query = this.queryService.getBarges();
        ArrayList<VesselDTO> vsls = new ArrayList();
        Connection conn = this.databaseConnection.connectDatabase();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while(rs.next()) {
            VesselDTO vsl = new VesselDTO();
            vsl.setVesselName(rs.getString("vesselName"));
            vsl.setVesselInboundId(rs.getString("visitIn"));
            vsl.setVesselOutboundId(rs.getString("visitOut"));
            vsl.setVesselETA(DateUtil.parseDateTime(rs.getTimestamp("ETA")));
            vsl.setVesselETD(DateUtil.parseDateTime(rs.getTimestamp("ETD")));
            vsl.setVesselStatus(VesselStatusUtil.getLabelByStatus(rs.getString("visitstatus")));
            vsls.add(vsl);
        }

        return vsls;
    }
}