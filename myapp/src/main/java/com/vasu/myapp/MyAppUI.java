package com.vasu.myapp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.annotation.WebServlet;

import com.fasterxml.uuid.Generators;
import com.myapp.controller.ESExpensesController;
import com.myapp.controller.ESSummaryController;
import com.myapp.model.Expenses;
import com.myapp.model.Summary;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.contextmenu.ContextMenu;
import com.vaadin.contextmenu.MenuItem;
import com.vaadin.data.HasValue;
import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import de.steinwedel.messagebox.MessageBox;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of an HTML page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("valo")
public class MyAppUI extends UI {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		final HorizontalLayout main = new HorizontalLayout();
		final VerticalLayout left = new VerticalLayout();
		final VerticalLayout right = new VerticalLayout();

		Panel l = new Panel();
		l.setCaption("Menu");
		l.setHeight("700px");
		l.setWidth("200px");

		Panel r = new Panel();
		r.setCaption("Body");
		r.setHeight("700px");
		r.setWidth("1300px");

		Button home = new Button("Home");
		home.setStyleName(ValoTheme.BUTTON_BORDERLESS);

		Button expMgr = new Button("ExpenseManager");
		expMgr.setStyleName(ValoTheme.BUTTON_BORDERLESS);

		left.addComponent(home);
		left.addComponent(expMgr);

		Label test = new Label();
		VerticalLayout tab1 = new VerticalLayout();
		tab1.addComponent(test);

		Label test1 = new Label();
		VerticalLayout tab2 = new VerticalLayout();
		tab2.addComponent(test1);

		Label test2 = new Label();
		VerticalLayout tab3 = new VerticalLayout();
		tab3.addComponent(test2);

		Label test3 = new Label();
		VerticalLayout tab4 = new VerticalLayout();
		tab4.addComponent(test3);

		Label test4 = new Label();
		VerticalLayout tab5 = new VerticalLayout();
		tab5.addComponent(test4);

		TabSheet tabs = new TabSheet();
		tabs.addTab(tab1, "Summary", null);
		tabs.addTab(tab2, "Insert", null);
		tabs.addTab(tab3, "View", null);
		tabs.addTab(tab4, "Arrears", null);
		tabs.addTab(tab5, "Borrows", null);

		l.setContent(left);
		r.setContent(right);
		main.addComponent(l);
		main.addComponent(r);
		setContent(main);

		tabs.addSelectedTabChangeListener(new SelectedTabChangeListener() {
			@SuppressWarnings("unchecked")
			public void selectedTabChange(SelectedTabChangeEvent event) {
				TabSheet tabsheet = event.getTabSheet();
				Layout tab = (Layout) tabsheet.getSelectedTab();
				String caption = tabsheet.getTab(tab).getCaption();
				if (caption.equalsIgnoreCase("Summary")) {
					tab1.removeAllComponents();
					Summary summaryObj = new Summary();
					ESSummaryController esSumControl = new ESSummaryController();

					VerticalLayout v1 = new VerticalLayout();
					HorizontalLayout h2 = new HorizontalLayout();

					final ComboBox<String> month = new ComboBox();
					List<String> mon = new ArrayList<>();
					mon.add("Jan");
					mon.add("Feb");
					mon.add("Mar");
					mon.add("Apr");
					mon.add("May");
					mon.add("Jun");
					mon.add("July");
					mon.add("Aug");
					mon.add("Sep");
					mon.add("Oct");
					mon.add("Nov");
					mon.add("Dec");
					month.setCaption("month");
					month.setItems(mon);

					final ComboBox<String> year = new ComboBox();
					List<String> yr = new ArrayList<>();
					yr.add("2018");
					yr.add("2019");
					yr.add("2020");
					yr.add("2021");
					yr.add("2022");
					yr.add("2023");
					yr.add("2024");
					yr.add("2025");
					yr.add("2026");
					yr.add("2027");
					yr.add("2028");
					yr.add("2029");
					yr.add("2030");
					year.setCaption("Year");
					year.setItems(yr);

					h2.addComponents(month, year);
					v1.addComponent(h2);

					FormLayout sumary = new FormLayout();
					TextField t1 = new TextField("TotalAmount");
					t1.setRequiredIndicatorVisible(true);
					if (t1.getValue() != null) {
						MessageBox.createInfo().withCaption("TotalAmount")
								.withMessage("Total Amount Not entered please enter").withOkButton().open();
					}

					TextField t2 = new TextField("AmountSpent");
					t2.setEnabled(false);
					TextField t3 = new TextField("RemainingAmount");
					t3.setEnabled(false);
					TextField t4 = new TextField("Arrears");
					t4.setEnabled(false);
					TextField t5 = new TextField("Borrows");
					t5.setEnabled(false);
					Button sumBtn = new Button("Add");

					sumBtn.setStyleName(ValoTheme.BUTTON_FRIENDLY);
					sumary.addComponents(t1, t2, t3, t4, t5, sumBtn);
					v1.addComponent(sumary);
					tab1.addComponent(v1);

					UUID id = Generators.timeBasedGenerator().generate();

					sumBtn.addClickListener(new ClickListener() {
						@Override
						public void buttonClick(ClickEvent event) {
							boolean contains = esSumControl.searchIndexForRecordExistance(month.getValue(),
									year.getValue());
							if (!contains) {
								summaryObj.setId(id.toString());

								if (!t1.getValue().isEmpty()) {
									summaryObj.setTotalAmount(Double.valueOf(t1.getValue()));
								} else {
									MessageBox.createWarning().withCaption("TotalAmount")
											.withMessage("Enter Total Amount").withOkButton().open();
								}
								summaryObj.setMonth(month.getValue());
								summaryObj.setYear(year.getValue());
								String insertStatus = esSumControl.insertSummary(summaryObj);
								MessageBox.createInfo().withCaption("Status").withMessage(insertStatus).open();
								t1.setEnabled(false);
							} else {
								MessageBox.createWarning().withCaption("Record Existed")
										.withMessage("Record Existed for this month").withOkButton().open();
								double value = esSumControl.getDataFromIndex(month.getValue(), year.getValue());
								t1.setValue(String.valueOf(value));
								t1.setEnabled(false);
							}
						}
					});

				} else if (caption.equalsIgnoreCase("Insert")) {
					tab2.removeAllComponents();

					Expenses expInsert = new Expenses();

					HorizontalLayout hInsert = new HorizontalLayout();
					FormLayout formLayout1 = new FormLayout();
					FormLayout formLayout2 = new FormLayout();
					FormLayout formLayout3 = new FormLayout();

					final DateField expDate = new DateField();
					expDate.setCaption("Date");
					expDate.setRequiredIndicatorVisible(true);

					final TextField name = new TextField();
					name.setCaption("Desc");
					name.setRequiredIndicatorVisible(true);
					name.setStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);

					final TextField url = new TextField("Url");
					url.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS);

					final TextField amount = new TextField();
					amount.setCaption("Amount");
					amount.setRequiredIndicatorVisible(true);

					final CheckBox isArrier = new CheckBox();
					isArrier.setCaption("isArrier");

					final ComboBox<String> category = new ComboBox();
					List<String> categ = new ArrayList<>();
					categ.add("Kirana");
					categ.add("Bills");
					categ.add("Borrows");
					categ.add("Arriers");
					categ.add("Movies");
					categ.add("Misc");
					category.setCaption("Category");
					category.setItems(categ);

					final ComboBox subCategory = new ComboBox();
					subCategory.setCaption("SubCategory");

					final ComboBox ArrierTo = new ComboBox();
					List<String> ArrierToList = new ArrayList<>();

					ArrierToList.add("Vasu");
					ArrierToList.add("Kumar");
					ArrierToList.add("Chaitu");
					ArrierToList.add("Sueseela");
					ArrierToList.add("Priya");
					ArrierTo.setCaption("ArrierTo");
					ArrierTo.setItems(ArrierToList);

					final CheckBox isBorrowed = new CheckBox();
					isBorrowed.setCaption("isBorrowed");

					final ComboBox borrowedTo = new ComboBox();
					List<String> borrowedToList = new ArrayList<>();
					borrowedToList.add("Vasu");
					borrowedToList.add("Kumar");
					borrowedToList.add("Chaitu");
					borrowedToList.add("Sueseela");
					borrowedToList.add("Priya");
					borrowedTo.setCaption("BorrowedTo");
					borrowedTo.setItems(borrowedToList);

					final TextArea remarks = new TextArea();
					remarks.setCaption("Remarks");

					Button button = new Button("insert");
					button.setStyleName(ValoTheme.BUTTON_FRIENDLY);

					formLayout1.addComponents(expDate, name, category, subCategory, amount);
					formLayout2.addComponents(isArrier, ArrierTo, isBorrowed, borrowedTo);
					formLayout3.addComponents(remarks, url, button);

					hInsert.addComponent(formLayout1);
					hInsert.addComponent(formLayout2);
					hInsert.addComponent(formLayout3);

					tab2.addComponent(hInsert);

					isArrier.addValueChangeListener(new ValueChangeListener<Boolean>() {
						public void valueChange(ValueChangeEvent<Boolean> event) {
							if (event.getValue()) {
								ArrierTo.setRequiredIndicatorVisible(true);
								isBorrowed.setEnabled(false);
								borrowedTo.setEnabled(false);
							} else {
								ArrierTo.setRequiredIndicatorVisible(false);
								isBorrowed.setEnabled(true);
								borrowedTo.setEnabled(true);
							}

						}
					});

					isBorrowed.addValueChangeListener(new ValueChangeListener<Boolean>() {
						@Override
						public void valueChange(ValueChangeEvent<Boolean> event) {
							if (event.getValue()) {
								borrowedTo.setRequiredIndicatorVisible(true);
								isArrier.setEnabled(false);
								ArrierTo.setEnabled(false);
							} else {
								borrowedTo.setRequiredIndicatorVisible(false);
								isArrier.setEnabled(true);
								ArrierTo.setEnabled(true);
							}
						}
					});

					category.addValueChangeListener(new ValueChangeListener() {
						public void valueChange(ValueChangeEvent event) {
							List billsCategory = null;
							if (event.getValue() == "Bills") {
								billsCategory = new ArrayList<>();
								billsCategory.add("Maintenance");
								billsCategory.add("Rent");
								billsCategory.add("Current");
								billsCategory.add("Internet");
								billsCategory.add("Cable");
								subCategory.setItems(billsCategory);
								subCategory.setValue("Rent");

							} else {
								billsCategory = new ArrayList<>();
								billsCategory.add("");
								subCategory.setItems(billsCategory);
							}
						}
					});

					button.addClickListener(new ClickListener() {
						@Override
						public void buttonClick(ClickEvent event) {
							if (null == expDate.getValue()) {
								MessageBox.createWarning().withCaption("Error").withMessage("Enter Date").withOkButton()
										.open();
								return;
							}

							if (name.getValue().equalsIgnoreCase("")) {
								MessageBox.createWarning().withCaption("Error").withMessage("please enter description")
										.withOkButton().open();
								return;
							}

							if (amount.getValue().equalsIgnoreCase("")) {
								MessageBox.createWarning().withCaption("Error").withMessage("please enter amount")
										.withOkButton().open();
								return;
							}

							UUID id = Generators.timeBasedGenerator().generate();

							expInsert.setId(String.valueOf(id));
							expInsert.setDate(expDate.getValue().toString());
							expInsert.setDescription(name.getValue());
							expInsert.setCategory(category.getValue());
							expInsert.setSubCategory(String.valueOf(subCategory.getValue()));
							expInsert.setAmount(Double.valueOf(amount.getValue()));
							expInsert.setArrier(isArrier.getValue());
							expInsert.setArrierTo(String.valueOf(ArrierTo.getValue()));
							expInsert.setBorrowed(isBorrowed.getValue());
							expInsert.setBorrowedTo(String.valueOf(borrowedTo.getValue()));
							expInsert.setUrl(url.getValue());
							expInsert.setRemarks(remarks.getValue());

							ESExpensesController insertCont = new ESExpensesController();
							insertCont.insertToES(expInsert);
						}
					});
				} else if (caption.equalsIgnoreCase("View")) {
					tab3.removeAllComponents();
					VerticalLayout viewLayout = new VerticalLayout();

					HorizontalLayout dates = new HorizontalLayout();
					Button load = new Button();
					load.setStyleName(ValoTheme.BUTTON_SMALL);
					load.setDescription("get data for current month");
					load.setIcon(VaadinIcons.DOWNLOAD_ALT);

					DateField from = new DateField();
					from.setDescription("select from date");
					from.setValue(LocalDate.now());
					from.setDateFormat("yyyy-MM-dd");
					from.setStyleName(ValoTheme.DATEFIELD_SMALL);

					DateField to = new DateField();
					to.setDescription("select to date");
					to.setValue(LocalDate.now());
					to.setDateFormat("yyyy-MM-dd");
					to.setStyleName(ValoTheme.DATEFIELD_SMALL);

					TextField search = new TextField();
					search.setPlaceholder("search Description...");

					search.setStyleName(ValoTheme.TEXTFIELD_ALIGN_RIGHT);

					dates.addComponent(from);
					dates.addComponent(to);

					Button submitQuery = new Button();
					submitQuery.addStyleName(ValoTheme.BUTTON_SMALL);
					submitQuery.setIcon(VaadinIcons.PLAY);

					dates.addComponent(submitQuery);
					dates.addComponent(load);
					dates.addComponent(search);

					ESExpensesController esCont = new ESExpensesController();
					Grid<Expenses> grid = new Grid<>(Expenses.class);
					grid.setSizeFull();
					viewLayout.addComponent(dates);

					search.addValueChangeListener(new ValueChangeListener<String>() {
						public void valueChange(ValueChangeEvent<String> event) {
							onNameFilterTextChange(event, grid);
						}
					});

					load.addClickListener(new Button.ClickListener() {
						public void buttonClick(ClickEvent event) {
							// load one month data..
							LocalDate todaydate = LocalDate.now();
							List<Expenses> exp = esCont.getData(todaydate.withDayOfMonth(1).toString(),
									esCont.DateToString(new Date()));
							if (!exp.isEmpty()) {
								grid.removeAllColumns();
								grid.setItems(exp);
								grid.addColumn(Expenses::getId).setCaption("Id").setHidden(true);
								grid.addColumn(Expenses::getDescription).setCaption("Description");
								grid.addColumn(Expenses::getAmount).setCaption("Amount");
								grid.addColumn(Expenses::getCategory).setCaption("Category");
								grid.addColumn(Expenses::getSubCategory).setCaption("subCategory");
								grid.addColumn(Expenses::getDate).setCaption("created");
								grid.addColumn(Expenses::getRemarks).setCaption("remarks");
								grid.addColumn(Expenses::isArrier).setCaption("isArrier");
								grid.addColumn(Expenses::getArrierTo).setCaption("arrierTo");
								grid.addColumn(Expenses::isBorrowed).setCaption("isBorrowed");
								grid.addColumn(Expenses::getBorrowedTo).setCaption("BorrowedBy");
							} else {
								MessageBox.createError().withCaption("Data")
										.withMessage("No Expenses Found in this month").open();
							}
						}
					});

					submitQuery.addClickListener(new Button.ClickListener() {
						public void buttonClick(ClickEvent event) {
							// selected ranges....
							String fromValue = from.getValue().toString();
							String toValue = to.getValue().toString();
							List<Expenses> exp = esCont.getData(fromValue, toValue);
							if (!exp.isEmpty()) {
								grid.removeAllColumns();
								grid.setItems(exp);
								grid.addColumn(Expenses::getId).setCaption("Id").setHidden(true);
								grid.addColumn(Expenses::getDescription).setCaption("Description");
								grid.addColumn(Expenses::getAmount).setCaption("Amount");
								grid.addColumn(Expenses::getCategory).setCaption("Category");
								grid.addColumn(Expenses::getSubCategory).setCaption("subCategory");
								grid.addColumn(Expenses::getDate).setCaption("created");
								grid.addColumn(Expenses::getRemarks).setCaption("remarks");
								grid.addColumn(Expenses::isArrier).setCaption("isArrier");
								grid.addColumn(Expenses::getArrierTo).setCaption("arrierTo");
								grid.addColumn(Expenses::isBorrowed).setCaption("isBorrowed");
								grid.addColumn(Expenses::getBorrowedTo).setCaption("BorrowedBy");
							} else {
								MessageBox.createError().withCaption("Data")
										.withMessage("No Expenses Found between selected dates").open();
							}

							if (null == from.getValue()) {
								MessageBox.createError().withCaption("Error in Dates")
										.withMessage("Select from date Please").open();
								return;
							}

							if (null == to.getValue()) {
								MessageBox.createError().withCaption("Error in Dates")
										.withMessage("Select to date Please").open();
								return;
							}
						}
					});
					viewLayout.addComponent(grid);
					tab3.addComponent(viewLayout);

					ContextMenu contextMenu = new ContextMenu(grid, true);
					@SuppressWarnings("unused")
					MenuItem view = contextMenu.addItem("View", e -> {
						Set<Expenses> abc = grid.getSelectedItems();
						for (Expenses viewItems : abc) {
							FormLayout forLay = new FormLayout();
							forLay.addComponent(new Label("Date : " + viewItems.getDate()));
							forLay.addComponent(new Label("ExpenseName : " + viewItems.getDescription()));
							forLay.addComponent(new Label("Amount : " + viewItems.getAmount()));
							forLay.addComponent(new Label("Category : " + viewItems.getCategory()));
							if (viewItems.isArrier()) {
								forLay.addComponent(new Label("Is Arrier : " + viewItems.isArrier()));
								forLay.addComponent(new Label("ArrierTo : " + viewItems.getArrierTo()));
							}
							if (viewItems.isBorrowed()) {
								forLay.addComponent(new Label("Is Borrowed : " + viewItems.isBorrowed()));
								forLay.addComponent(new Label("BorrowedBy : " + viewItems.getBorrowedTo()));
							}
							if (viewItems.getUrl() != null) {
								forLay.addComponent(new Label("url : " + viewItems.getUrl()));
							}
							MessageBox.create().withCaption("ViewExpense").withMessage(forLay).withOkButton().open();
						}
					});

					MenuItem edit = contextMenu.addItem("edit", e -> {
						Set<Expenses> abc = grid.getSelectedItems();
						HorizontalLayout hInsert = new HorizontalLayout();
						FormLayout formLayout1 = new FormLayout();
						FormLayout formLayout2 = new FormLayout();
						FormLayout formLayout3 = new FormLayout();

						final DateField expDate = new DateField();
						expDate.setCaption("Date");
						expDate.setRequiredIndicatorVisible(true);

						final TextField name = new TextField();
						name.setCaption("Desc");
						name.setRequiredIndicatorVisible(true);
						name.setStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);

						final TextField url = new TextField("Url");
						url.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS);

						final TextField amount = new TextField();
						amount.setCaption("Amount");
						amount.setRequiredIndicatorVisible(true);

						final CheckBox isArrier = new CheckBox();
						isArrier.setCaption("isArrier");

						final ComboBox<String> category = new ComboBox();
						List<String> categ = new ArrayList<>();
						categ.add("Kirana");
						categ.add("Bills");
						categ.add("Borrows");
						categ.add("Arriers");
						categ.add("Movies");
						categ.add("Misc");
						category.setCaption("Category");
						category.setValue("Veges");
						category.setItems(categ);

						final ComboBox subCategory = new ComboBox();
						subCategory.setCaption("SubCategory");

						final ComboBox ArrierTo = new ComboBox();
						List<String> ArrierToList = new ArrayList<>();

						ArrierToList.add("Vasu");
						ArrierToList.add("Kumar");
						ArrierToList.add("Chaitu");
						ArrierToList.add("Sueseela");
						ArrierToList.add("Priya");
						ArrierTo.setCaption("ArrierTo");
						ArrierTo.setValue("Vasu");
						ArrierTo.setItems(ArrierToList);

						final CheckBox isBorrowed = new CheckBox();
						isBorrowed.setCaption("isBorrowed");

						final ComboBox borrowedTo = new ComboBox();
						List<String> borrowedToList = new ArrayList<>();
						borrowedToList.add("Vasu");
						borrowedToList.add("Kumar");
						borrowedToList.add("Chaitu");
						borrowedToList.add("Sueseela");
						borrowedToList.add("Priya");
						borrowedTo.setCaption("BorrowedTo");
						borrowedTo.setValue("Vasu");
						borrowedTo.setItems(borrowedToList);

						final TextArea remarks = new TextArea();
						remarks.setCaption("Remarks");

						Button updateBtn = new Button("update");
						updateBtn.setStyleName(ValoTheme.BUTTON_FRIENDLY);

						formLayout1.addComponents(expDate, name, category, subCategory, amount);
						formLayout1.setSpacing(true);
						formLayout2.addComponents(isArrier, ArrierTo, isBorrowed, borrowedTo);
						formLayout2.setSpacing(true);
						formLayout3.addComponents(remarks, url, updateBtn);

						hInsert.addComponent(formLayout1);
						hInsert.addComponent(formLayout2);
						hInsert.addComponent(formLayout3);
						
                     	Window subWindow = new Window("update");
                     	subWindow.setHeight("500px");
                     	subWindow.setWidth("900px");
                     	subWindow.setResizable(false);
						subWindow.setContent(hInsert);
						subWindow.center();
						addWindow(subWindow);
						
						ESExpensesController controller = new ESExpensesController();
						Expenses expUpdate = new Expenses();

						for (Expenses viewItems : abc) {
							expDate.setValue(LocalDate.parse(viewItems.getDate()));
							name.setValue(viewItems.getDescription());

							if (viewItems.getUrl() != null) {
								url.setValue(viewItems.getUrl());
							}

							amount.setValue(String.valueOf(viewItems.getAmount()));
							isArrier.setValue(viewItems.isArrier());
							category.setValue(viewItems.getCategory());
							subCategory.setValue(viewItems.getSubCategory());
							ArrierTo.setValue(viewItems.getArrierTo());
							isBorrowed.setValue(viewItems.isBorrowed());
							borrowedTo.setValue(viewItems.getBorrowedTo());
							remarks.setValue(viewItems.getRemarks());

							if (viewItems.isArrier()) {
								formLayout2.removeComponent(isBorrowed);
								formLayout2.removeComponent(borrowedTo);
							}

							if (viewItems.isBorrowed()) {
								formLayout2.removeComponent(isArrier);
								formLayout2.removeComponent(ArrierTo);
							}

						
							updateBtn.addClickListener(new ClickListener() {
								@Override
								public void buttonClick(ClickEvent event) {
									expUpdate.setId(viewItems.getId());
									expUpdate.setDescription(name.getValue());
									expUpdate.setAmount(Double.valueOf(amount.getValue()));
									expUpdate.setDate(expDate.getValue().toString());
									expUpdate.setArrier(isArrier.getValue());
									expUpdate.setCategory(category.getValue());
									expUpdate.setSubCategory(String.valueOf(subCategory.getValue()));
									expUpdate.setArrierTo(String.valueOf(ArrierTo.getValue()));
									expUpdate.setBorrowedTo(String.valueOf(borrowedTo.getValue()));
									expUpdate.setBorrowed(isBorrowed.getValue());
									expUpdate.setRemarks(remarks.getValue());
									expUpdate.setUrl(url.getValue());

									String mesg = controller.updateToES(expUpdate);
									MessageBox.create().withCaption("updateInfo").withMessage(mesg).open();

								}
							});

						}
					});
				}
			}
		});

		home.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				right.removeAllComponents();
				right.addComponent(new Label("Home"));
			}
		});

		expMgr.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				right.removeAllComponents();
				right.addComponent(tabs);

				tab1.removeAllComponents();
				Summary summaryObj = new Summary();
				ESSummaryController esSumControl = new ESSummaryController();

				VerticalLayout v1 = new VerticalLayout();
				HorizontalLayout h2 = new HorizontalLayout();

				final ComboBox<String> month = new ComboBox();
				List<String> mon = new ArrayList<>();
				mon.add("Jan");
				mon.add("Feb");
				mon.add("Mar");
				mon.add("Apr");
				mon.add("May");
				mon.add("Jun");
				mon.add("July");
				mon.add("Aug");
				mon.add("Sep");
				mon.add("Oct");
				mon.add("Nov");
				mon.add("Dec");
				month.setCaption("month");
				month.setItems(mon);

				@SuppressWarnings("rawtypes")
				final ComboBox<String> year = new ComboBox();
				List<String> yr = new ArrayList<>();
				yr.add("2018");
				yr.add("2019");
				yr.add("2020");
				yr.add("2021");
				yr.add("2022");
				yr.add("2023");
				yr.add("2024");
				yr.add("2025");
				yr.add("2026");
				yr.add("2027");
				yr.add("2028");
				yr.add("2029");
				yr.add("2030");
				year.setCaption("Year");
				year.setItems(yr);

				h2.addComponents(month, year);
				v1.addComponent(h2);

				FormLayout sumary = new FormLayout();
				TextField t1 = new TextField("TotalAmount");
				t1.setRequiredIndicatorVisible(true);

				if (t1.getValue() == null) {
					MessageBox.createInfo().withCaption("TotalAmount")
							.withMessage("Total Amount Not entered please enter").withOkButton().open();
				}

				TextField t2 = new TextField("AmountSpent");
				t2.setEnabled(false);
				TextField t3 = new TextField("RemainingAmount");
				t3.setEnabled(false);
				TextField t4 = new TextField("Arrears");
				t4.setEnabled(false);
				TextField t5 = new TextField("Borrows");
				t5.setEnabled(false);
				Button sumBtn = new Button("Add");

				sumBtn.setStyleName(ValoTheme.BUTTON_FRIENDLY);
				sumary.addComponents(t1, t2, t3, t4, t5, sumBtn);
				v1.addComponent(sumary);
				tab1.addComponent(v1);

				UUID id = Generators.timeBasedGenerator().generate();

				sumBtn.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						if (month.getValue() != null && year.getValue() != null) {
							summaryObj.setMonth(month.getValue());
							summaryObj.setYear(year.getValue());
							boolean contains = esSumControl.searchIndexForRecordExistance(month.getValue(),
									year.getValue());
							if (!contains) {
								if (!t1.getValue().isEmpty()) {
									summaryObj.setId(id.toString());
									summaryObj.setTotalAmount(Double.valueOf(t1.getValue()));
									String insertStatus = esSumControl.insertSummary(summaryObj);
									MessageBox.createInfo().withCaption("Status").withMessage(insertStatus).open();
									t1.setEnabled(false);
								} else {
									MessageBox.createWarning().withCaption("TotalAmount")
											.withMessage("Enter Total Amount").withOkButton().open();
								}
							} else {
								MessageBox.createWarning().withCaption("Record Existed")
										.withMessage("Record Existed for this month").withOkButton().open();
								double value = esSumControl.getDataFromIndex(month.getValue(), year.getValue());
								t1.setValue(String.valueOf(value));
								t1.setEnabled(false);
							}
						} else {
							MessageBox.createWarning().withCaption("Month and Year")
									.withMessage("select month and year").withOkButton().open();
						}
					}
				});
			}
		});
	}

	@WebServlet(urlPatterns = "/*", name = "MyAppUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyAppUI.class, productionMode = false)
	public static class MyAppUIServlet extends VaadinServlet {
	}

	private void onNameFilterTextChange(HasValue.ValueChangeEvent<String> event, Grid<Expenses> grid) {
		ListDataProvider<Expenses> dataProvider = (ListDataProvider<Expenses>) grid.getDataProvider();
		dataProvider.setFilter(Expenses::getDescription, s -> caseInsensitiveContains(s, event.getValue()));
	}

	private Boolean caseInsensitiveContains(String where, String what) {
		return where.toLowerCase().contains(what.toLowerCase());
	}
}
