package com.apm.terminals.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class QueryService {
    @Value("${vessel.not.phases}")
    private String vesselPhases;
    @Value("${vessel.afterdate}")
    private String vesselAfterDate;
    @Value("${vessel.beforedate}")
    private String vesselBeforeDate;
    @Value("${barge.not.phases}")
    private String bargePhases;
    @Value("${barge.afterdate}")
    private String bargeAfterDate;
    @Value("${barge.beforedate}")
    private String bargeBeforeDate;
    @Value("${barge.services}")
    private String bargeServices;
    @Value("${truck.event.type.key}")
    private int eventTypeKey;
    @Value("${truck.transaction.status}")
    private String truckTransactionStatus;
    @Value("${truck.sub.type}")
    private String truckSubType;
    @Value("${truck.transit.state}")
    private String transitState;
    @Value("${vessel.service.barges}")
    private String vesselServiceBarges;
    @Value("${permission.history.user.system}")
    private String userSystem;
    @Value("${permission.history.replace.user.system}")
    private String replaceUserSystem;

    public QueryService() {
    }

    public String getVessels() {
        return String.format("Set nocount ON\r\nSELECT v.name AS vesselName,\r\nvvd.ib_vyg AS visitIn,\r\nvvd.ob_vyg AS visitOut,  \r\nisnull(vd.eta,'') as ETA,\r\nisnull(vd.etd, '') as ETD,\r\nvvd.cargo_cutoff as startDry,\r\nvvd.cargo_cutoff as cutOff,\r\nvvd.reefer_cutoff as reeferCutoff,\r\nvd.ffd as vencT1,\r\ncv.phase AS visitstatus  \r\nFROM argo_carrier_visit cv  \r\ninner join argo_visit_details vd ON vd.gkey = cv.cvcvd_gkey  \r\ninner join vsl_vessel_visit_details vvd ON vvd.vvd_gkey = cv.cvcvd_gkey  \r\ninner join vsl_vessels v ON v.gkey = vvd.vessel_gkey  \r\nWHERE cv.carrier_mode = 'VESSEL' and vd.service != (%s) \r\nAND vd.eta < getdate() + %s  \r\nAND getdate() - %s < vd.eta  \r\nAND cv.phase NOT IN (%s) \r\nORDER BY vd.eta DESC", this.vesselServiceBarges, this.vesselAfterDate, this.vesselBeforeDate, this.vesselPhases);
    }

    public String getBarges() {
        return String.format("SET nocount ON  \r\nselect v.name AS vesselName,  \r\nvvd.ib_vyg AS visitIn,  \r\nvvd.ob_vyg AS visitOut,\r\nisnull(vd.eta,'') as ETA,\r\nisnull(vd.etd, '') as ETD,\r\ncv.phase AS visitStatus  \r\nfrom argo_carrier_visit cv \r\ninner JOIN argo_visit_details vd ON vd.gkey = cv.cvcvd_gkey \r\ninner JOIN vsl_vessel_visit_details vvd ON vvd.vvd_gkey = cv.cvcvd_gkey \r\ninner JOIN vsl_vessels v ON v.gkey = vvd.vessel_gkey \r\nwhere cv.carrier_mode = 'VESSEL' \r\nAND vd.service in (%s)  \r\nAND vd.eta < GETDATE() + %s  \r\nAND GETDATE() -%s < vd.eta  \r\nAND cv.phase not in (%s) \r\nORDER BY vd.eta DESC", this.bargeServices, this.bargeAfterDate, this.bargeBeforeDate, this.bargePhases);
    }

    public String getPermissionsByUnitId(String unitId) {
        String query = "SET nocount ON\r\nselect \r\nu.id as unitId,\r\nclientf.reference_id as permissionId,\r\nCONCAT(ovess.name,' ',ovvd.ib_vyg,'/',ovvd.ob_vyg) as vesselVisit,\r\n(select count(f.gkey) from srv_flags f inner join srv_flag_types ft on ft.gkey = f.flag_type_gkey where ft.id = 'CUS_ORDER' and f.applied_to_gkey = u.gkey and f.reference_id like (clientf.reference_id + '%')) as total_block_count, \r\n(select count(f.gkey) from srv_flags f inner join srv_flag_types ft on ft.gkey = f.flag_type_gkey where ft.id = 'CUS_ORDER' and f.applied_to_gkey = u.gkey and f.reference_id like (clientf.reference_id + '%') and f.gkey not in (select blocked_flag_gkey from srv_vetos)) as active_block_count,\r\n(select max(e.gkey) from srv_event e inner join srv_event_types et on et.gkey = e.event_type_gkey inner join srv_event_field_changes efc on efc.event_gkey = e.gkey where et.id = 'CUSTOMS_LOAD_PRM_APPROVED' and efc.metafield_id = 'flagGkey' and efc.new_value = convert(varchar , clientf.gkey)) as release_perm_event_gkey,\r\nclientf.applied_to_gkey as appliedToGkey \r\nfrom inv_unit u \r\ninner join inv_unit_fcy_visit ufv on ufv.unit_gkey = u.gkey \r\ninner join argo_carrier_visit obcv on obcv.gkey = ufv.actual_ob_cv \r\ninner join vsl_vessel_visit_details ovvd on ovvd.vvd_gkey = obcv.cvcvd_gkey \r\ninner join vsl_vessels ovess on ovess.gkey = ovvd.vessel_gkey \r\nleft join srv_flags clientf on clientf.applied_to_gkey = u.gkey and clientf.gkey not in (select distinct blocked_flag_gkey from srv_vetos)and clientf.flag_type_gkey = (select gkey from srv_flag_types where id = 'CUSTOMS PRM') \r\nwhere 1 = 1 \r\nand clientf.reference_id is not null \r\nand u.category in ('EXPRT','TRSHP') \r\nand u.freight_kind in ('FCL','LCL') \r\nand ufv.transit_state not in ('S99_RETIRED') \r\nand obcv.phase not in ('70CLOSED') \r\n";
        query = query + String.format("and u.id = '%s'", unitId);
        return query;
    }

    public String getTruckStatusByUnitId(String unitId) {
        return String.format("SELECT\r\napplied_to_natural_key as unitId,   \r\nrtvd.truck_license_nbr as truckLicense,  \r\nUPPER(rtvd.driver_name) as truckDriver,   \r\nrga.requested_time as appointmentDate,  \r\nrtts.stage_start as truckVisitInDate,  \r\nsrv_event.placed_time as truckLoadContainerDate,   \r\nDATEDIFF(MI,srv_event.placed_time, CURRENT_TIMESTAMP) as truckLoadToNow,\r\nrtt.bl_nbr as billOfLading   \r\nFROM srv_event \r\ninner join srv_event_types evt on srv_event.event_type_gkey = evt.gkey  \r\ninner join road_truck_transactions rtt on rtt.ctr_id = srv_event.applied_to_natural_key   \r\ninner join road_gate_appointment rga on rga.id = rtt.appointment_nbr   \r\ninner join road_truck_transaction_stages rtts on rtts.tran_gkey = rtt.gkey   \r\ninner join road_truck_visit_details rtvd on rtvd.tvdtls_gkey = rtt.truck_visit_gkey\t   \r\ninner join inv_unit u on u.id = rtt.ctr_id   \r\ninner join inv_unit_fcy_visit ufv on ufv.unit_gkey = u.gkey   \r\nwhere event_type_gkey = 238 \r\nand applied_to_natural_key in ('%s')\r\nand (placed_time between Dateadd(dd,-1, Convert( char(8) , Current_timestamp, 112 )) and Current_timestamp  )\t  \r\nand rtt.status in ('%s')   \r\nand rtt.sub_type in ('%s')   \r\nand ufv.transit_state in (%s)   \r\norder by truckLoadToNow desc", unitId, this.truckTransactionStatus, this.truckSubType, this.transitState);
    }

    public String getPermissionsHistoryById(String appliedToGkey, String permissionId) {
        return String.format("select \r\ne.placed_time as 'placedTime',\r\nCASE WHEN e.placed_by = '%s' THEN '%s ' ELSE e.placed_by END as 'placedBy',\r\net.label as 'action',\r\ne.keyword_value_2 as 'vesselVisit',\r\ne.note as 'notes',\r\ne.keyword_value_1 as 'permissionId'\r\nfrom srv_event e\r\ninner join srv_event_types et on et.gkey = e.event_type_gkey\r\nwhere e.applied_to_gkey = %s and e.keyword_value_1 = '%s' ORDER BY e.gkey ASC", this.userSystem, this.replaceUserSystem, appliedToGkey, permissionId);
    }
}