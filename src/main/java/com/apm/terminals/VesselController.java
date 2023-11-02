package com.apm.terminals;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apm.terminals.dto.VesselDTO;
import com.apm.terminals.services.VesselService;

 
@Controller
@RequestMapping({"vesselServices"})
public class VesselController {
    @Autowired
    VesselService vesselService;

    public VesselController() {
    }

    @GetMapping(
            value = {"/getVessels"},
            produces = {"application/json"}
    )
    @ResponseBody
    public ArrayList<VesselDTO> getVessels() throws SQLException {
        ArrayList<VesselDTO> vessels = this.vesselService.getVessels();
        return vessels;
    }

    @GetMapping(
            value = {"/getBarges"},
            produces = {"application/json"}
    )
    @ResponseBody
    public ArrayList<VesselDTO> getBarges() throws SQLException {
        ArrayList<VesselDTO> vessels = this.vesselService.getBarges();
        return vessels;
    }
}
