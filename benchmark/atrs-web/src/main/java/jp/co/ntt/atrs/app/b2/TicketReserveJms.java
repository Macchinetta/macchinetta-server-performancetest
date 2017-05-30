/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.b2;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import jp.co.ntt.atrs.app.b0.SelectFlightForm;
import jp.co.ntt.atrs.app.b1.FlightSearchResultOutputDto;
import jp.co.ntt.atrs.app.b1.TicketSearchForm;
import jp.co.ntt.atrs.app.b1.TicketSearchHelper;
import jp.co.ntt.atrs.domain.model.FareTypeCd;
import jp.co.ntt.atrs.domain.model.Flight;
import jp.co.ntt.atrs.domain.model.JmsInput;
import jp.co.ntt.atrs.domain.service.b2.TicketReserveService;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * 空席照会。
 * @author NTT 電電次郎
 */
@Component
public class TicketReserveJms {

    @Inject
    TicketSearchHelper ticketSearchHelper;

    @Inject
    TicketReserveHelper ticketReserveHelper;

    @Inject
    TicketReserveService ticketReserveService;

    @JmsListener(containerFactory = "jmsListenerContainerFactory", destination = "java:comp/env/jms/queue/RequestQueue")
    @SendTo("java:comp/env/jms/queue/ResponseQueue")
    public ReserveCompleteOutputDto registerReservationJms(JmsInput jmsInput) {

        TicketSearchForm ticketSearchForm = new TicketSearchForm();
        ticketSearchForm.getFlightSearchCriteriaForm().setMonth(
                jmsInput.getMonth());
        ticketSearchForm.getFlightSearchCriteriaForm()
                .setDay(jmsInput.getDay());
        ticketSearchForm.getFlightSearchCriteriaForm().setTime(
                jmsInput.getTime());
        ticketSearchForm.getFlightSearchCriteriaForm().setBoardingClassCd(
                jmsInput.getBoardingClassCd());
        ticketSearchForm.getFlightSearchCriteriaForm().setDepAirportCd(
                jmsInput.getDepAirportCd());
        ticketSearchForm.getFlightSearchCriteriaForm().setArrAirportCd(
                jmsInput.getArrAirportCd());

        Pageable pageable = new PageRequest(0, 1000);
        FlightSearchResultOutputDto flightSearchResultOutputDto = ticketSearchHelper
                .searchFlight(ticketSearchForm, pageable);

        SelectFlightForm selectFlightForm = new SelectFlightForm();
        selectFlightForm.setFlightName(flightSearchResultOutputDto
                .getTicketSearchResultDto().getFlightVacantInfoPage()
                .getContent().get(0).getFlightMaster().getFlightName());
        selectFlightForm.setBoardingClassCd(ticketSearchForm
                .getFlightSearchCriteriaForm().getBoardingClassCd());
        selectFlightForm.setDepartureDate(flightSearchResultOutputDto
                .getDepDate());
        selectFlightForm.setFareTypeCd(FareTypeCd.OW);

        List<SelectFlightForm> selectFlightFormList = new ArrayList<SelectFlightForm>();
        selectFlightFormList.add(selectFlightForm);

        List<Flight> flightList = ticketReserveHelper
                .createFlightList(selectFlightFormList);

        return ticketReserveHelper.reserveJms(ticketReserveService
                .findMember(jmsInput.getCustomerNo()), flightList);
    }

}
