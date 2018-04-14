package com.example.giftcard.gui;

import com.example.giftcard.command.api.IssueCmd;
import com.example.giftcard.command.api.RedeemCmd;
import com.example.giftcard.query.api.CardSummary;
import com.vaadin.annotations.Push;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

@SpringUI
@Push
public class GiftcardUI extends UI {

    private final static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    private CardSummaryDataProvider cardSummaryDataProvider;

    public GiftcardUI(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        TabSheet tabsheet = new TabSheet();
        tabsheet.addTab(cardsTab());
        tabsheet.addTab(batchTab());

        setContent(tabsheet);

        UI.getCurrent().setErrorHandler(new DefaultErrorHandler() {
            @Override
            public void error(com.vaadin.server.ErrorEvent event) {
                Throwable cause = event.getThrowable();
                log.error("an error occurred", cause);
                while(cause.getCause() != null) cause = cause.getCause();
                Notification.show("Error", cause.getMessage(), Notification.Type.ERROR_MESSAGE);
            }
        });
    }

    private VerticalLayout cardsTab() {
        HorizontalLayout commandBar = new HorizontalLayout();
        commandBar.setSizeFull();
        commandBar.addComponents(issuePanel(), redeemPanel());

        VerticalLayout layout = new VerticalLayout();
        layout.addComponents(commandBar, summaryGrid());
        layout.setHeight(95, Unit.PERCENTAGE);
        layout.setCaption("Cards");

        return layout;
    }

    private VerticalLayout batchTab() {
        HorizontalLayout commandBar = new HorizontalLayout();
        commandBar.setSizeFull();
        commandBar.addComponents(issuePanel());

        VerticalLayout layout = new VerticalLayout();
        layout.addComponents(commandBar);
        layout.setHeight(95, Unit.PERCENTAGE);
        layout.setCaption("Batch");

        return layout;
    }

    @Override
    public void close() {
        cardSummaryDataProvider.shutDown();
        super.close();
    }

    private Panel issuePanel() {
        TextField id = new TextField("Card id");
        TextField amount = new TextField("Amount");
        Button submit = new Button("Submit");

        submit.addClickListener(evt -> {
            commandGateway.sendAndWait(new IssueCmd(id.getValue(), Integer.parseInt(amount.getValue())));
            Notification.show("Success", Notification.Type.HUMANIZED_MESSAGE);
        });

        FormLayout form = new FormLayout();
        form.addComponents(id, amount, submit);
        form.setMargin(true);

        Panel panel = new Panel("Issue single card");
        panel.setContent(form);
        return panel;
    }

    private Panel redeemPanel() {
        TextField id = new TextField("Card id");
        TextField amount = new TextField("Amount");
        Button submit = new Button("Submit");

        submit.addClickListener(evt -> {
            commandGateway.sendAndWait(new RedeemCmd(id.getValue(), Integer.parseInt(amount.getValue())));
            Notification.show("Success", Notification.Type.HUMANIZED_MESSAGE);
        });

        FormLayout form = new FormLayout();
        form.addComponents(id, amount, submit);
        form.setMargin(true);

        Panel panel = new Panel("Redeem card");
        panel.setContent(form);
        return panel;
    }

    private Grid summaryGrid() {
        cardSummaryDataProvider = new CardSummaryDataProvider(queryGateway);
        Grid<CardSummary> grid = new Grid<>();
        grid.addColumn(CardSummary::getId).setCaption("Card ID");
        grid.addColumn(CardSummary::getInitialValue).setCaption("Initial value");
        grid.addColumn(CardSummary::getIssuedAt).setCaption("Issued at");
        grid.addColumn(CardSummary::getRemainingValue).setCaption("Remaining value");
        grid.setSizeFull();
        grid.setDataProvider(cardSummaryDataProvider);
        return grid;
    }

}
