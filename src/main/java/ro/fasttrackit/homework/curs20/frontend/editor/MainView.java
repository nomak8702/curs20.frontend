package ro.fasttrackit.homework.curs20.frontend.editor;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.util.StringUtils;
import ro.fasttrackit.homework.curs20.frontend.editor.TransactionEditor;
import ro.fasttrackit.homework.curs20.frontend.entities.Transaction;
import ro.fasttrackit.homework.curs20.frontend.repository.TransactionRepository;

@Route
@Theme(value = Lumo.class,variant = Lumo.DARK)
public class MainView extends VerticalLayout {

    private final TransactionRepository transactionRepository;
    private final TransactionEditor editor;
    final Grid<Transaction> grid;
    final TextField filter;
    private final Button addNewButton;

    public MainView(TransactionRepository repo, TransactionEditor editor) {

        this.transactionRepository = repo;
        this.editor = editor;
        this.grid = new Grid<>(Transaction.class);
        this.filter = new TextField();
        this.addNewButton = new Button("New transaction", VaadinIcon.PLUS.create());

        HorizontalLayout actions = new HorizontalLayout(filter, addNewButton);
        add(actions, grid, editor);

        grid.setHeight("200px");
        grid.setColumns("id", "product", "type", "amount");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        filter.setPlaceholder("Filter by product");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listTransactions(e.getValue()));
        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editTransaction(e.getValue());
        });

        addNewButton.addClickListener(e -> editor.editTransaction(new Transaction("", "", "")));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listTransactions(filter.getValue());
        });

        listTransactions(null);
    }

    void listTransactions(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(transactionRepository.findAll());
        } else {
            grid.setItems(transactionRepository.findByProductStartsWithIgnoreCase(filterText));
        }

    }
}
