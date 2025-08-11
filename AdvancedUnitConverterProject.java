
// Import necessary Swing components and utility classes
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Main class for the Advanced Unit Converter application with a Graphical User
 * Interface (GUI).
 * This program provides a robust, GUI-driven interface for converting values
 * between various units. It uses Java Swing for the UI and a well-structured
 * enum-based system for the conversion logic.
 *
 * This version is a significant upgrade, featuring:
 * - A tabbed GUI for better organization.
 * - Support for compound units (e.g., Velocity, Data Transfer Rate).
 * - A history log to track all conversions in a session.
 * - Dynamic GUI elements that change based on user selections.
 * - This version is self-contained and does not require external libraries.
 *
 * Author: Dilseerat Kaur
 * Project: Advanced Unit Converter with GUI
 */
public class AdvancedUnitConverterProject {

    // --- ENUMS for Unit Definitions ---

    /**
     * Enum for different categories of units.
     * This provides a type-safe way to represent the conversion categories.
     * It has been massively expanded to include many new scientific categories.
     */
    public enum UnitCategory {
        LENGTH("Length"),
        VOLUME("Volume"),
        AREA("Area"),
        WEIGHT("Weight"),
        TEMPERATURE("Temperature"),
        DATA("Data Storage"),
        TIME("Time"),
        ENERGY("Energy"),
        PRESSURE("Pressure"),
        VELOCITY("Velocity"),
        FLOW_RATE("Flow Rate"),
        POWER("Power"),
        FORCE("Force"),
        FUEL_CONSUMPTION("Fuel Consumption"),
        DATA_TRANSFER("Data Transfer Rate");

        private final String displayName;

        UnitCategory(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    /**
     * Enum for all specific units.
     * Each unit is defined with its category, its display symbol, and a
     * conversion factor to a common base unit for its category.
     */
    public enum Unit {
        // --- Length Units (Base Unit: METER) ---
        NANOMETER(UnitCategory.LENGTH, "nm", 1e-9),
        MICROMETER(UnitCategory.LENGTH, "µm", 1e-6),
        MILLIMETER(UnitCategory.LENGTH, "mm", 0.001),
        CENTIMETER(UnitCategory.LENGTH, "cm", 0.01),
        METER(UnitCategory.LENGTH, "m", 1.0),
        KILOMETER(UnitCategory.LENGTH, "km", 1000.0),
        INCH(UnitCategory.LENGTH, "in", 0.0254),
        FOOT(UnitCategory.LENGTH, "ft", 0.3048),
        YARD(UnitCategory.LENGTH, "yd", 0.9144),
        MILE(UnitCategory.LENGTH, "mi", 1609.34),
        NAUTICAL_MILE(UnitCategory.LENGTH, "nmi", 1852.0),
        ANGSTROM(UnitCategory.LENGTH, "Å", 1e-10),
        ASTRONOMICAL_UNIT(UnitCategory.LENGTH, "AU", 1.496e11),
        LIGHT_YEAR(UnitCategory.LENGTH, "ly", 9.461e15),
        PARSEC(UnitCategory.LENGTH, "pc", 3.086e16),

        // --- Volume Units (Base Unit: CUBIC_METER) ---
        CUBIC_METER(UnitCategory.VOLUME, "m³", 1.0),
        CUBIC_CENTIMETER(UnitCategory.VOLUME, "cm³", 1e-6),
        CUBIC_FOOT(UnitCategory.VOLUME, "ft³", 0.0283168),
        CUBIC_INCH(UnitCategory.VOLUME, "in³", 1.63871e-5),
        MILLILITER(UnitCategory.VOLUME, "ml", 1e-6),
        LITER(UnitCategory.VOLUME, "L", 0.001),
        GALLON(UnitCategory.VOLUME, "gal", 0.00378541),
        FLUID_OUNCE_US(UnitCategory.VOLUME, "fl oz (US)", 2.95735e-5),
        TEASPOON(UnitCategory.VOLUME, "tsp", 4.92892e-6),
        TABLESPOON(UnitCategory.VOLUME, "tbsp", 1.47868e-5),
        PINT_US(UnitCategory.VOLUME, "pt (US)", 0.000473176),
        QUART_US(UnitCategory.VOLUME, "qt (US)", 0.000946353),

        // --- Area Units (Base Unit: SQUARE_METER) ---
        SQUARE_MILLIMETER(UnitCategory.AREA, "mm²", 1e-6),
        SQUARE_CENTIMETER(UnitCategory.AREA, "cm²", 1e-4),
        SQUARE_METER(UnitCategory.AREA, "m²", 1.0),
        SQUARE_KILOMETER(UnitCategory.AREA, "km²", 1000000.0),
        SQUARE_INCH(UnitCategory.AREA, "in²", 0.00064516),
        SQUARE_FOOT(UnitCategory.AREA, "ft²", 0.092903),
        SQUARE_YARD(UnitCategory.AREA, "yd²", 0.836127),
        SQUARE_MILE(UnitCategory.AREA, "mi²", 2589988.11),
        ACRE(UnitCategory.AREA, "acre", 4046.86),
        HECTARE(UnitCategory.AREA, "ha", 10000.0),

        // --- Weight Units (Base Unit: KILOGRAM) ---
        MILLIGRAM(UnitCategory.WEIGHT, "mg", 1e-6),
        GRAM(UnitCategory.WEIGHT, "g", 0.001),
        KILOGRAM(UnitCategory.WEIGHT, "kg", 1.0),
        METRIC_TON(UnitCategory.WEIGHT, "tonne", 1000.0),
        OUNCE(UnitCategory.WEIGHT, "oz", 0.0283495),
        POUND(UnitCategory.WEIGHT, "lb", 0.453592),
        STONE(UnitCategory.WEIGHT, "st", 6.35029),
        SHORT_TON(UnitCategory.WEIGHT, "short ton", 907.185),
        CARAT(UnitCategory.WEIGHT, "ct", 0.0002),

        // --- Temperature Units (No linear conversion, handled separately) ---
        CELSIUS(UnitCategory.TEMPERATURE, "°C", 0.0),
        FAHRENHEIT(UnitCategory.TEMPERATURE, "°F", 0.0),
        KELVIN(UnitCategory.TEMPERATURE, "K", 0.0),
        RANKINE(UnitCategory.TEMPERATURE, "°R", 0.0),

        // --- Data Storage Units (Base Unit: BYTE) ---
        BIT(UnitCategory.DATA, "bit", 0.125),
        BYTE(UnitCategory.DATA, "B", 1.0),
        KILOBYTE(UnitCategory.DATA, "KB", 1024.0),
        MEGABYTE(UnitCategory.DATA, "MB", 1024.0 * 1024.0),
        GIGABYTE(UnitCategory.DATA, "GB", 1024.0 * 1024.0 * 1024.0),
        TERABYTE(UnitCategory.DATA, "TB", 1024.0 * 1024.0 * 1024.0 * 1024.0),
        PETABYTE(UnitCategory.DATA, "PB", 1024.0 * 1024.0 * 1024.0 * 1024.0 * 1024.0),
        EXABYTE(UnitCategory.DATA, "EB", 1024.0 * 1024.0 * 1024.0 * 1024.0 * 1024.0 * 1024.0),

        // --- Time Units (Base Unit: SECOND) ---
        NANOSECOND(UnitCategory.TIME, "ns", 1e-9),
        MICROSECOND(UnitCategory.TIME, "µs", 1e-6),
        MILLISECOND(UnitCategory.TIME, "ms", 0.001),
        SECOND(UnitCategory.TIME, "s", 1.0),
        MINUTE(UnitCategory.TIME, "min", 60.0),
        HOUR(UnitCategory.TIME, "hr", 3600.0),
        DAY(UnitCategory.TIME, "day", 86400.0),
        WEEK(UnitCategory.TIME, "week", 604800.0),
        MONTH(UnitCategory.TIME, "month", 2.628e6),
        YEAR(UnitCategory.TIME, "year", 3.154e7),
        DECADE(UnitCategory.TIME, "decade", 3.154e8),
        CENTURY(UnitCategory.TIME, "century", 3.154e9),

        // --- Energy Units (Base Unit: JOULE) ---
        JOULE(UnitCategory.ENERGY, "J", 1.0),
        KILOJOULE(UnitCategory.ENERGY, "kJ", 1000.0),
        CALORIE(UnitCategory.ENERGY, "cal", 4.184),
        KILOCALORIE(UnitCategory.ENERGY, "kcal", 4184.0),
        WATT_HOUR(UnitCategory.ENERGY, "Wh", 3600.0),
        KILOWATT_HOUR(UnitCategory.ENERGY, "kWh", 3.6e6),
        ELECTRON_VOLT(UnitCategory.ENERGY, "eV", 1.60218e-19),
        BRITISH_THERMAL_UNIT(UnitCategory.ENERGY, "BTU", 1055.06),

        // --- Pressure Units (Base Unit: PASCAL) ---
        PASCAL(UnitCategory.PRESSURE, "Pa", 1.0),
        KILOPASCAL(UnitCategory.PRESSURE, "kPa", 1000.0),
        MEGAPASCAL(UnitCategory.PRESSURE, "MPa", 1e6),
        BAR(UnitCategory.PRESSURE, "bar", 1e5),
        MILLIBAR(UnitCategory.PRESSURE, "mbar", 100.0),
        ATMOSPHERE(UnitCategory.PRESSURE, "atm", 101325.0),
        PSI(UnitCategory.PRESSURE, "psi", 6894.76),
        TORR(UnitCategory.PRESSURE, "torr", 133.322),
        INCH_OF_MERCURY(UnitCategory.PRESSURE, "inHg", 3386.39),

        // --- Power Units (Base Unit: WATT) ---
        WATT(UnitCategory.POWER, "W", 1.0),
        KILOWATT(UnitCategory.POWER, "kW", 1000.0),
        MEGAWATT(UnitCategory.POWER, "MW", 1e6),
        HORSEPOWER_METRIC(UnitCategory.POWER, "hp (metric)", 735.499),
        HORSEPOWER(UnitCategory.POWER, "hp", 745.7),

        // --- Force Units (Base Unit: NEWTON) ---
        NEWTON(UnitCategory.FORCE, "N", 1.0),
        KILONEWTON(UnitCategory.FORCE, "kN", 1000.0),
        DYNE(UnitCategory.FORCE, "dyn", 1e-5),
        POUND_FORCE(UnitCategory.FORCE, "lbf", 4.44822),
        KILOGRAM_FORCE(UnitCategory.FORCE, "kgf", 9.80665);

        private final UnitCategory category;
        private final String symbol;
        private final double toBaseFactor;

        Unit(UnitCategory category, String symbol, double toBaseFactor) {
            this.category = category;
            this.symbol = symbol;
            this.toBaseFactor = toBaseFactor;
        }

        public UnitCategory getCategory() {
            return category;
        }

        public String getSymbol() {
            return symbol;
        }

        @Override
        public String toString() {
            return name().replace('_', ' ');
        }
    }

    // --- CONVERSION LOGIC CLASSES ---

    /**
     * A common interface for all converter types, demonstrating polymorphism.
     */
    public interface Converter {
        double convert(double value, Object from, Object to);
    }

    /**
     * Handles the non-linear temperature conversions, a complex part of the logic.
     */
    private static double convertTemperature(double value, Unit fromUnit, Unit toUnit) {
        double valueInKelvin = 0;
        switch (fromUnit) {
            case CELSIUS:
                valueInKelvin = value + 273.15;
                break;
            case FAHRENHEIT:
                valueInKelvin = (value - 32) * 5.0 / 9.0 + 273.15;
                break;
            case KELVIN:
                valueInKelvin = value;
                break;
            case RANKINE:
                valueInKelvin = value * 5.0 / 9.0;
                break;
        }

        switch (toUnit) {
            case CELSIUS:
                return valueInKelvin - 273.15;
            case FAHRENHEIT:
                return (valueInKelvin - 273.15) * 9.0 / 5.0 + 32;
            case KELVIN:
                return valueInKelvin;
            case RANKINE:
                return valueInKelvin * 9.0 / 5.0;
            default:
                return value;
        }
    }

    /**
     * Handles standard conversions between two units of the same category.
     * Implements the `Converter` interface.
     */
    public static class SimpleConverter implements Converter {
        @Override
        public double convert(double value, Object from, Object to) {
            Unit fromUnit = (Unit) from;
            Unit toUnit = (Unit) to;
            if (fromUnit.getCategory() != toUnit.getCategory()) {
                throw new IllegalArgumentException("Cannot convert between different unit categories.");
            }

            if (fromUnit.getCategory() == UnitCategory.TEMPERATURE) {
                return convertTemperature(value, fromUnit, toUnit);
            }

            double valueInBaseUnit = value * fromUnit.toBaseFactor;
            double convertedValue = valueInBaseUnit / toUnit.toBaseFactor;
            return convertedValue;
        }
    }

    /**
     * Handles compound conversions, e.g., Velocity (Length/Time).
     * This is a key advanced feature.
     */
    public static class CompoundConverter implements Converter {
        private final UnitCategory numeratorCategory;
        private final UnitCategory denominatorCategory;

        public CompoundConverter(UnitCategory numCat, UnitCategory denCat) {
            this.numeratorCategory = numCat;
            this.denominatorCategory = denCat;
        }

        @Override
        public double convert(double value, Object from, Object to) {
            @SuppressWarnings("unchecked")
            JComboBox<Unit> fromNumerator = ((List<JComboBox<Unit>>) from).get(0);
            @SuppressWarnings("unchecked")
            JComboBox<Unit> fromDenominator = ((List<JComboBox<Unit>>) from).get(1);
            @SuppressWarnings("unchecked")
            JComboBox<Unit> toNumerator = ((List<JComboBox<Unit>>) to).get(0);
            @SuppressWarnings("unchecked")
            JComboBox<Unit> toDenominator = ((List<JComboBox<Unit>>) to).get(1);

            Unit fromNumUnit = (Unit) fromNumerator.getSelectedItem();
            Unit fromDenUnit = (Unit) fromDenominator.getSelectedItem();
            Unit toNumUnit = (Unit) toNumerator.getSelectedItem();
            Unit toDenUnit = (Unit) toDenominator.getSelectedItem();

            if (fromNumUnit == null || fromDenUnit == null || toNumUnit == null || toDenUnit == null) {
                throw new IllegalArgumentException("Please select all units.");
            }

            // Convert numerator value to its category's base unit.
            double valueInBaseNumerator = new SimpleConverter().convert(value, fromNumUnit,
                    fromNumUnit.getCategory() == UnitCategory.LENGTH ? Unit.METER
                            : fromNumUnit.getCategory() == UnitCategory.DATA ? Unit.BYTE
                                    : fromNumUnit.getCategory() == UnitCategory.VOLUME ? Unit.CUBIC_METER : null);

            // Convert denominator value to its category's base unit.
            double valueInBaseDenominator = new SimpleConverter().convert(1.0, fromDenUnit,
                    fromDenUnit.getCategory() == UnitCategory.TIME ? Unit.SECOND
                            : fromDenUnit.getCategory() == UnitCategory.LENGTH ? Unit.METER : null);

            // Calculate the compound value in its base representation.
            double valueInCompoundBase = valueInBaseNumerator / valueInBaseDenominator;

            // Convert compound base value to the target numerator and denominator units.
            double targetDenominatorInBase = new SimpleConverter().convert(1.0, toDenUnit,
                    toDenUnit.getCategory() == UnitCategory.TIME ? Unit.SECOND
                            : toDenUnit.getCategory() == UnitCategory.LENGTH ? Unit.METER : null);

            double targetNumeratorInBase = valueInCompoundBase * targetDenominatorInBase;

            return new SimpleConverter().convert(targetNumeratorInBase,
                    toNumUnit.getCategory() == UnitCategory.LENGTH ? Unit.METER
                            : toNumUnit.getCategory() == UnitCategory.DATA ? Unit.BYTE
                                    : toNumUnit.getCategory() == UnitCategory.VOLUME ? Unit.CUBIC_METER : null,
                    toNumUnit);
        }
    }

    // --- GUI Components and Logic ---

    // UI Components
    private JFrame frame;
    private JTabbedPane tabbedPane;
    private JComboBox<UnitCategory> categoryComboBox;
    private JComboBox<Unit> fromUnitComboBox, toUnitComboBox;
    private JComboBox<Unit> fromDenominatorComboBox, toDenominatorComboBox;
    private JFormattedTextField inputField;
    private JComboBox<Integer> precisionComboBox;
    private JLabel resultLabel;
    private JLabel slashFromLabel, slashToLabel;
    private JTextArea historyTextArea;
    private JPanel conversionPanel;
    private JList<UnitCategory> categoryList;
    private DefaultListModel<UnitCategory> categoryListModel;

    // Data and State
    private List<String> conversionHistory = new ArrayList<>();
    private Properties userSettings = new Properties();
    private static final String SETTINGS_FILE = "converter.properties";
    private static final String HISTORY_FILE = "history.log";

    public AdvancedUnitConverterProject() {
        loadSettings();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame = new JFrame("The Unit Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        setupMenuBar();
        setupTabbedPane();

        loadHistory();

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                saveSettings();
                saveHistory();
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> {
            saveSettings();
            saveHistory();
            System.exit(0);
        });
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAboutDialog());
        helpMenu.add(aboutItem);
        menuBar.add(helpMenu);

        frame.setJMenuBar(menuBar);
    }

    private void showAboutDialog() {
        JOptionPane.showMessageDialog(frame,
                "The Unit Converter\n\nVersion 1.0\n\nDeveloped by Dilseerat Kaur for a college project.",
                "About",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void setupTabbedPane() {
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Standard & Compound", createConversionPanel());
        tabbedPane.addTab("Manage Categories", createCategoryManagementPanel());
        tabbedPane.addTab("History", createHistoryPanel());
        frame.add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createCategoryManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Select Categories to Display", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        panel.add(title, BorderLayout.NORTH);

        categoryListModel = new DefaultListModel<>();
        for (UnitCategory cat : UnitCategory.values()) {
            categoryListModel.addElement(cat);
        }
        categoryList = new JList<>(categoryListModel);
        categoryList.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                if (super.isSelectedIndex(index0)) {
                    super.removeSelectionInterval(index0, index1);
                } else {
                    super.addSelectionInterval(index0, index1);
                }
            }
        });

        List<UnitCategory> savedCategories = Arrays.stream(userSettings.getProperty("visibleCategories", "").split(","))
                .filter(s -> !s.isEmpty())
                .map(UnitCategory::valueOf)
                .collect(Collectors.toList());
        for (UnitCategory cat : savedCategories) {
            categoryList.addSelectionInterval(Arrays.asList(UnitCategory.values()).indexOf(cat),
                    Arrays.asList(UnitCategory.values()).indexOf(cat));
        }

        JScrollPane scrollPane = new JScrollPane(categoryList);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton saveButton = new JButton("Save Categories");
        saveButton.addActionListener(e -> {
            List<UnitCategory> selectedCats = categoryList.getSelectedValuesList();
            userSettings.setProperty("visibleCategories",
                    selectedCats.stream().map(Enum::name).collect(Collectors.joining(",")));
            JOptionPane.showMessageDialog(frame, "Categories saved. Restart the app for changes to take full effect.",
                    "Saved", JOptionPane.INFORMATION_MESSAGE);
        });
        panel.add(saveButton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createConversionPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints topGbc = new GridBagConstraints();
        topGbc.insets = new Insets(5, 5, 5, 5);
        topGbc.fill = GridBagConstraints.HORIZONTAL;

        topGbc.gridx = 0;
        topGbc.gridy = 0;
        topGbc.anchor = GridBagConstraints.EAST;
        topPanel.add(new JLabel("Category:"), topGbc);
        topGbc.gridx = 1;
        topGbc.gridy = 0;
        topGbc.weightx = 1.0;
        topGbc.anchor = GridBagConstraints.WEST;
        categoryComboBox = new JComboBox<>();
        categoryComboBox.addActionListener(e -> updateUnitPanel());
        topPanel.add(categoryComboBox, topGbc);

        topGbc.gridx = 0;
        topGbc.gridy = 1;
        topGbc.anchor = GridBagConstraints.EAST;
        topPanel.add(new JLabel("Precision:"), topGbc);
        topGbc.gridx = 1;
        topGbc.gridy = 1;
        topGbc.weightx = 1.0;
        topGbc.anchor = GridBagConstraints.WEST;
        Integer[] precisions = { 2, 4, 6, 8, 12 };
        precisionComboBox = new JComboBox<>(precisions);
        precisionComboBox.setSelectedItem(4);
        topPanel.add(precisionComboBox, topGbc);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        conversionPanel = new JPanel(new GridBagLayout());
        mainPanel.add(conversionPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));

        JButton convertButton = new JButton("Convert");
        convertButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        convertButton.addActionListener(new ConvertButtonListener());
        bottomPanel.add(convertButton, BorderLayout.NORTH);

        resultLabel = new JLabel("Result: ", SwingConstants.CENTER);
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        bottomPanel.add(resultLabel, BorderLayout.CENTER);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        populateCategories();
        return mainPanel;
    }

    private void populateCategories() {
        // Clear all items first to prevent duplicates
        categoryComboBox.removeAllItems();

        List<UnitCategory> categories = Arrays.asList(UnitCategory.values());
        for (UnitCategory cat : categories) {
            categoryComboBox.addItem(cat);
        }
    }

    private JPanel createHistoryPanel() {
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        historyTextArea = new JTextArea();
        historyTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(historyTextArea);
        historyPanel.add(scrollPane, BorderLayout.CENTER);
        return historyPanel;
    }

    private void updateUnitPanel() {
        conversionPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        UnitCategory selectedCategory = (UnitCategory) categoryComboBox.getSelectedItem();
        boolean isCompound = (selectedCategory == UnitCategory.VELOCITY || selectedCategory == UnitCategory.FLOW_RATE
                || selectedCategory == UnitCategory.DATA_TRANSFER || selectedCategory == UnitCategory.FUEL_CONSUMPTION);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        conversionPanel.add(new JLabel("From Unit:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        fromUnitComboBox = new JComboBox<>();
        conversionPanel.add(fromUnitComboBox, gbc);

        if (isCompound) {
            slashFromLabel = new JLabel("/", SwingConstants.CENTER);
            gbc.gridx = 2;
            gbc.gridy = 0;
            gbc.weightx = 0.0;
            conversionPanel.add(slashFromLabel, gbc);

            fromDenominatorComboBox = new JComboBox<>();
            gbc.gridx = 3;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            conversionPanel.add(fromDenominatorComboBox, gbc);
        }

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        conversionPanel.add(new JLabel("To Unit:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        toUnitComboBox = new JComboBox<>();
        conversionPanel.add(toUnitComboBox, gbc);

        if (isCompound) {
            slashToLabel = new JLabel("/", SwingConstants.CENTER);
            gbc.gridx = 2;
            gbc.gridy = 1;
            gbc.weightx = 0.0;
            conversionPanel.add(slashToLabel, gbc);

            toDenominatorComboBox = new JComboBox<>();
            gbc.gridx = 3;
            gbc.gridy = 1;
            gbc.weightx = 1.0;
            conversionPanel.add(toDenominatorComboBox, gbc);
        }

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        conversionPanel.add(new JLabel("Value:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        inputField = new JFormattedTextField(NumberFormat.getNumberInstance());
        inputField.setColumns(10);
        conversionPanel.add(inputField, gbc);

        if (isCompound) {
            populateCompoundComboBoxes(selectedCategory);
        } else {
            populateStandardComboBoxes(selectedCategory);
        }

        conversionPanel.revalidate();
        conversionPanel.repaint();
    }

    private void populateStandardComboBoxes(UnitCategory category) {
        fromUnitComboBox.removeAllItems();
        toUnitComboBox.removeAllItems();
        List<Unit> unitsForCategory = Arrays.stream(Unit.values())
                .filter(unit -> unit.getCategory() == category)
                .collect(Collectors.toList());

        for (Unit unit : unitsForCategory) {
            fromUnitComboBox.addItem(unit);
            toUnitComboBox.addItem(unit);
        }
    }

    private void populateCompoundComboBoxes(UnitCategory category) {
        fromUnitComboBox.removeAllItems();
        toUnitComboBox.removeAllItems();
        fromDenominatorComboBox.removeAllItems();
        toDenominatorComboBox.removeAllItems();

        UnitCategory numeratorCategory, denominatorCategory;

        if (category == UnitCategory.VELOCITY) {
            numeratorCategory = UnitCategory.LENGTH;
            denominatorCategory = UnitCategory.TIME;
        } else if (category == UnitCategory.FLOW_RATE) {
            numeratorCategory = UnitCategory.VOLUME;
            denominatorCategory = UnitCategory.TIME;
        } else if (category == UnitCategory.DATA_TRANSFER) {
            numeratorCategory = UnitCategory.DATA;
            denominatorCategory = UnitCategory.TIME;
        } else if (category == UnitCategory.FUEL_CONSUMPTION) {
            numeratorCategory = UnitCategory.VOLUME;
            denominatorCategory = UnitCategory.LENGTH;
        } else {
            throw new IllegalArgumentException("Unsupported compound category.");
        }

        List<Unit> numeratorUnits = Arrays.stream(Unit.values())
                .filter(unit -> unit.getCategory() == numeratorCategory)
                .collect(Collectors.toList());
        List<Unit> denominatorUnits = Arrays.stream(Unit.values())
                .filter(unit -> unit.getCategory() == denominatorCategory)
                .collect(Collectors.toList());

        for (Unit unit : numeratorUnits) {
            fromUnitComboBox.addItem(unit);
            toUnitComboBox.addItem(unit);
        }

        for (Unit unit : denominatorUnits) {
            fromDenominatorComboBox.addItem(unit);
            toDenominatorComboBox.addItem(unit);
        }
    }

    private class ConvertButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Number number = (Number) inputField.getValue();
                if (number == null) {
                    resultLabel.setText("Error: Please enter a number.");
                    return;
                }

                double value = number.doubleValue();
                UnitCategory selectedCategory = (UnitCategory) categoryComboBox.getSelectedItem();

                double convertedValue;
                String fromSymbol, toSymbol;

                boolean isCompound = (selectedCategory == UnitCategory.VELOCITY
                        || selectedCategory == UnitCategory.FLOW_RATE || selectedCategory == UnitCategory.DATA_TRANSFER
                        || selectedCategory == UnitCategory.FUEL_CONSUMPTION);

                if (isCompound) {
                    Unit fromNumerator = (Unit) fromUnitComboBox.getSelectedItem();
                    Unit fromDenominator = (Unit) fromDenominatorComboBox.getSelectedItem();
                    Unit toNumerator = (Unit) toUnitComboBox.getSelectedItem();
                    Unit toDenominator = (Unit) toDenominatorComboBox.getSelectedItem();

                    if (fromNumerator == null || fromDenominator == null || toNumerator == null
                            || toDenominator == null) {
                        resultLabel.setText("Error: Please select all units.");
                        return;
                    }

                    fromSymbol = fromNumerator.getSymbol() + "/" + fromDenominator.getSymbol();
                    toSymbol = toNumerator.getSymbol() + "/" + toDenominator.getSymbol();

                    // Logic to find the correct base unit for the numerator and denominator
                    // categories
                    Unit numBaseUnit = getBaseUnit(fromNumerator.getCategory());
                    Unit denBaseUnit = getBaseUnit(fromDenominator.getCategory());

                    double valueInBaseNumerator = new SimpleConverter().convert(value, fromNumerator, numBaseUnit);
                    double valueInBaseDenominator = new SimpleConverter().convert(1.0, fromDenominator, denBaseUnit);

                    double valueInCompoundBase = valueInBaseNumerator / valueInBaseDenominator;

                    Unit toNumBaseUnit = getBaseUnit(toNumerator.getCategory());
                    Unit toDenBaseUnit = getBaseUnit(toDenominator.getCategory());

                    double convertedValueInBase = valueInCompoundBase;
                    if (toDenBaseUnit != denBaseUnit) {
                        double denFactor = new SimpleConverter().convert(1.0, toDenBaseUnit, denBaseUnit);
                        convertedValueInBase = convertedValueInBase / denFactor;
                    }

                    if (toNumBaseUnit != numBaseUnit) {
                        double numFactor = new SimpleConverter().convert(1.0, numBaseUnit, toNumBaseUnit);
                        convertedValueInBase = convertedValueInBase * numFactor;
                    }

                    convertedValue = new SimpleConverter().convert(convertedValueInBase, toNumBaseUnit, toNumerator);

                } else {
                    Unit fromUnit = (Unit) fromUnitComboBox.getSelectedItem();
                    Unit toUnit = (Unit) toUnitComboBox.getSelectedItem();
                    if (fromUnit == null || toUnit == null) {
                        resultLabel.setText("Error: Please select a valid unit.");
                        return;
                    }
                    fromSymbol = fromUnit.getSymbol();
                    toSymbol = toUnit.getSymbol();
                    convertedValue = new SimpleConverter().convert(value, fromUnit, toUnit);
                }

                Integer precision = (Integer) precisionComboBox.getSelectedItem();
                DecimalFormat df = new DecimalFormat("#,##0." + "0".repeat(precision));

                String resultText = String.format("Result: %s %s = %s %s",
                        df.format(value), fromSymbol,
                        df.format(convertedValue), toSymbol);
                resultLabel.setText(resultText);

                conversionHistory.add(0, resultText);
                historyTextArea.append(resultText + "\n");

            } catch (Exception ex) {
                resultLabel.setText("Error: " + ex.getMessage());
            }
        }
    }

    private Unit getBaseUnit(UnitCategory category) {
        switch (category) {
            case LENGTH:
                return Unit.METER;
            case TIME:
                return Unit.SECOND;
            case DATA:
                return Unit.BYTE;
            case VOLUME:
                return Unit.CUBIC_METER;
            default:
                return null;
        }
    }

    private void loadSettings() {
        try (InputStream input = new FileInputStream(SETTINGS_FILE)) {
            userSettings.load(input);
        } catch (IOException e) {
            System.err.println("Settings file not found, using defaults.");
        }
    }

    private void saveSettings() {
        try (OutputStream output = new FileOutputStream(SETTINGS_FILE)) {
            userSettings.store(output, "Unit Converter Settings");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader(HISTORY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                conversionHistory.add(line);
                historyTextArea.append(line + "\n");
            }
        } catch (IOException e) {
            System.err.println("History file not found, starting with a clean log.");
        }
    }

    private void saveHistory() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HISTORY_FILE))) {
            for (String entry : conversionHistory) {
                writer.write(entry);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdvancedUnitConverterProject());
    }
}
