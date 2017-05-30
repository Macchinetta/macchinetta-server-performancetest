/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.b1;

import jp.co.ntt.atrs.app.b0.TicketHelper;
import jp.co.ntt.atrs.app.common.exception.BadRequestException;
import jp.co.ntt.atrs.domain.model.Flight;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.springframework.web.bind.annotation.InitBinder;
import java.util.List;
import javax.inject.Inject;

/**
 * 空席照会コントローラ。
 * @author NTT 電電次郎
 */
@Controller
@RequestMapping(value = "Ticket/search")
public class TicketSearchController {

    /**
     * チケット予約共通Helper。
     */
    @Inject
    TicketHelper ticketHelper;

    /**
     * 空席照会Helper。
     */
    @Inject
    TicketSearchHelper ticketSearchHelper;

    /**
     * 空席照会フォームのバリデータ。
     */
    @Inject
    TicketSearchValidator ticketSearchValidator;

    /**
     * 空席照会フォームのバリデータをバインダに追加する。
     * @param binder バインダ
     */
    @InitBinder("ticketSearchForm")
    public void initBinderForTicketSearch(WebDataBinder binder) {
        binder.addValidators(ticketSearchValidator);
    }

    /**
     * 空席照会条件を初期設定して、トップページを表示する。
     * @param model 出力情報を保持するオブジェクト
     * @return View論理名
     */
    @RequestMapping(method = RequestMethod.GET, params = "topForm")
    public String searchTopForm(Model model) {
        model.addAttribute(ticketSearchHelper.createDefaultTicketSearchForm());
        return "B1/flightSearchForm-top";
    }

    /**
     * 空席情報照会条件入力画面を表示する。
     * <p>
     * 画面項目がデフォルトの空席情報照会条件を表示する。
     * </p>
     * @param model 出力情報を保持するオブジェクト
     * @return View論理名
     */
    @RequestMapping(method = RequestMethod.GET, params = "form")
    public String searchForm(Model model) {

        model.addAttribute(ticketSearchHelper.createDefaultTicketSearchForm());
        model.addAttribute(ticketSearchHelper.createSearchFlightFormOutputDto());

        return "B1/flightSearchForm";
    }

    /**
     * 空席情報照会条件入力画面を表示する。
     * <p>
     * 画面項目が前回の空席情報照会条件を表示する。
     * </p>
     * @param ticketSearchForm 空席照会フォーム
     * @param model 出力情報を保持するオブジェクト
     * @return View論理名
     */
    @RequestMapping(method = RequestMethod.GET, params = "redo")
    public String searchRedo(TicketSearchForm ticketSearchForm, Model model) {

        model.addAttribute(ticketSearchHelper.createSearchFlightFormOutputDto());

        return "B1/flightSearchForm";
    }

    /**
     * 空席照会を行い、空席照会結果画面を表示する。
     * @param ticketSearchForm 空席照会フォーム
     * @param result チェック結果
     * @param model 出力情報を保持するオブジェクト
     * @param pageable ページネーション検索条件
     * @return View論理名
     */
    @RequestMapping(method = RequestMethod.GET)
    public String search(@Validated TicketSearchForm ticketSearchForm,
            BindingResult result, Pageable pageable, Model model) {

        if (result.hasErrors()) {
            return searchRedo(ticketSearchForm, model);
        }

        try {
            model.addAttribute(ticketSearchHelper.searchFlight(
                    ticketSearchForm, pageable));
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
            return searchRedo(ticketSearchForm, model);
        }

        return "B1/flightSearchResult";
    }

    /**
     * 選択したフライト情報の業務ロジックチェックを行い、選択フライト確認画面を表示する。
     * <p>
     * 業務ロジックチェックNGの場合、空席情報照会結果画面を表示する。
     * </p>
     * @param ticketSearchForm 空席照会フォーム
     * @param result チェック結果
     * @param model 出力情報を保持するオブジェクト
     * @param pageable ページネーション検索条件
     * @return View論理名
     */
    @RequestMapping(method = RequestMethod.GET, params = "select")
    public String searchSelect(@Validated TicketSearchForm ticketSearchForm,
            BindingResult result, Pageable pageable, Model model) {

        if (result.hasErrors()) {
            throw new BadRequestException(result);
        }

        try {
            List<Flight> flightList = ticketHelper
                    .createFlightList(ticketSearchForm
                            .getSelectFlightFormList());
            ticketHelper.validateFlightList(flightList);
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
            ticketSearchForm.removeLastSelectedFlightForm();
            return search(ticketSearchForm, result, pageable, model);
        }

        return "forward:/Ticket/reserve?select";
    }

}
