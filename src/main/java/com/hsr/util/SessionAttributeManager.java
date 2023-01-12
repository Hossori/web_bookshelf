package com.hsr.util;

import java.time.ZoneId;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hsr.constant.GlobalConst;
import com.hsr.rest.Result;

@RestController
@RequestMapping("/rest/session")
public class SessionAttributeManager {

    @PutMapping("/put/zoneId")
    public Result saveZoneIdToSession(HttpServletRequest request, String zoneId) {
        if (zoneId == null || !ZoneId.getAvailableZoneIds().contains(zoneId)) {
            zoneId = ZoneId.systemDefault().toString();
        }
        request.getSession().setAttribute(GlobalConst.CLIENT_ZONE_ID_KEY, zoneId);
        return new Result(HttpStatus.OK.value(), null);
    }

    public static ZoneId getClientZoneId(HttpServletRequest request) {
        String clientZoneId = String.valueOf(
                request.getSession().getAttribute(GlobalConst.CLIENT_ZONE_ID_KEY)
        );
        return ZoneId.of(clientZoneId);
    }

}
