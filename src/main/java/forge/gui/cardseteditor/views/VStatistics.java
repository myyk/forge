package forge.gui.cardseteditor.views;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;
import forge.gui.cardseteditor.SEditorUtil;
import forge.gui.cardseteditor.controllers.CStatistics;
import forge.gui.framework.DragCell;
import forge.gui.framework.DragTab;
import forge.gui.framework.EDocID;
import forge.gui.framework.IVDoc;
import forge.gui.toolbox.FLabel;
import forge.gui.toolbox.FSkin;

/** 
 * Assembles Swing components of cardset editor analysis tab.
 *
 * <br><br><i>(V at beginning of class name denotes a view class.)</i>
 */
public enum VStatistics implements IVDoc<CStatistics> {
    /** */
    SINGLETON_INSTANCE;

    // Fields used with interface IVDoc
    private DragCell parentCell;
    private final DragTab tab = new DragTab("Statistics");

    // Global stats
    private JLabel lblTotal = new FLabel.Builder()
            .text("Total cards: 0").tooltip("TOTAL CARDS")
            .fontStyle(Font.BOLD).fontSize(11).fontStyle(Font.BOLD).build();
    private JLabel lblTMC = new FLabel.Builder()
            .text("Total mana cost: 0").tooltip("TOTAL MANA COST")
            .fontStyle(Font.BOLD).fontSize(11).fontStyle(Font.BOLD).build();
    private JLabel lblAMC = new FLabel.Builder()
            .text("Average mana cost: 0.00").tooltip("AVERAGE MANA COST")
            .fontStyle(Font.BOLD).fontSize(11).fontStyle(Font.BOLD).build();

    // Total and color count labels
    private final JPanel pnlStats = new JPanel();
    private final JLabel lblMulti = buildLabel(SEditorUtil.StatTypes.MULTICOLOR, true);
    private final JLabel lblBlack = buildLabel(SEditorUtil.StatTypes.BLACK, false);
    private final JLabel lblBlue = buildLabel(SEditorUtil.StatTypes.BLUE, true);
    private final JLabel lblGreen = buildLabel(SEditorUtil.StatTypes.GREEN, false);
    private final JLabel lblRed = buildLabel(SEditorUtil.StatTypes.RED, true);
    private final JLabel lblWhite = buildLabel(SEditorUtil.StatTypes.WHITE, false);
    private final JLabel lblColorless = buildLabel(SEditorUtil.StatTypes.COLORLESS, true);

    // Card type labels
    private final JLabel lblArtifact = buildLabel(SEditorUtil.StatTypes.ARTIFACT, true);
    private final JLabel lblCreature = buildLabel(SEditorUtil.StatTypes.CREATURE, false);
    private final JLabel lblEnchantment = buildLabel(SEditorUtil.StatTypes.ENCHANTMENT, true);
    private final JLabel lblInstant = buildLabel(SEditorUtil.StatTypes.INSTANT, false);
    private final JLabel lblLand = buildLabel(SEditorUtil.StatTypes.LAND, true);
    private final JLabel lblPlaneswalker = buildLabel(SEditorUtil.StatTypes.PLANESWALKER, false);
    private final JLabel lblSorcery = buildLabel(SEditorUtil.StatTypes.SORCERY, true);

    // CMC labels
    private final JLabel lblCMC0 = buildLabel(
            new ImageIcon(FSkin.getImage(FSkin.ColorlessManaImages.IMG_0, 16, 16)), true);
    private final JLabel lblCMC1 = buildLabel(
            new ImageIcon(FSkin.getImage(FSkin.ColorlessManaImages.IMG_1, 16, 16)), false);
    private final JLabel lblCMC2 = buildLabel(
            new ImageIcon(FSkin.getImage(FSkin.ColorlessManaImages.IMG_2, 16, 16)), true);
    private final JLabel lblCMC3 = buildLabel(
            new ImageIcon(FSkin.getImage(FSkin.ColorlessManaImages.IMG_3, 16, 16)), false);
    private final JLabel lblCMC4 = buildLabel(
            new ImageIcon(FSkin.getImage(FSkin.ColorlessManaImages.IMG_4, 16, 16)), true);
    private final JLabel lblCMC5 = buildLabel(
            new ImageIcon(FSkin.getImage(FSkin.ColorlessManaImages.IMG_5, 16, 16)), false);
    private final JLabel lblCMC6 = buildLabel(
            new ImageIcon(FSkin.getImage(FSkin.ColorlessManaImages.IMG_6, 16, 16)), true);

    // Layout containers
    private final JScrollPane scroller = new JScrollPane(pnlStats);

    //========== Constructor
    private VStatistics() {
        scroller.setOpaque(false);
        scroller.getViewport().setOpaque(false);
        scroller.setBorder(null);
        scroller.getViewport().setBorder(null);
        scroller.getVerticalScrollBar().setUnitIncrement(16);

        // Color stats
        lblMulti.setToolTipText("Total Card Count");
        lblBlack.setToolTipText("Black Card Count");
        lblBlue.setToolTipText("Blue Card Count");
        lblGreen.setToolTipText("Green Card Count");
        lblRed.setToolTipText("Red Card Count");
        lblWhite.setToolTipText("White Card Count");
        lblColorless.setToolTipText("Total Card Count");

        // Type stats
        lblArtifact.setToolTipText("Artifact Card Count");
        lblCreature.setToolTipText("Creature Card Count");
        lblColorless.setToolTipText("Colorless Card Count");
        lblEnchantment.setToolTipText("Enchantment Card Count");
        lblInstant.setToolTipText("Instant Card Count");
        lblLand.setToolTipText("Land Card Count");
        lblPlaneswalker.setToolTipText("Planeswalker Card Count");
        lblSorcery.setToolTipText("Sorcery Card Count");

        // Stats container
        pnlStats.setOpaque(false);
        pnlStats.setLayout(new MigLayout("insets 0, gap 0, ax center, wrap 3"));

        pnlStats.add(lblTotal, "w 96%!, h 20px!, span 3 1, gap 2% 0 0 0");
        pnlStats.add(lblTMC, "w 96%!, h 20px!, span 3 1, gap 2% 0 0 0");
        pnlStats.add(lblAMC, "w 96%!, h 20px!, span 3 1, gap 2% 0 0 0");

        // Add labels to container
        final String constraints = "w 32%!, h 35px!";
        pnlStats.add(lblMulti, constraints);
        pnlStats.add(lblArtifact, constraints);
        pnlStats.add(lblCMC0, constraints);

        pnlStats.add(lblBlack, constraints);
        pnlStats.add(lblCreature, constraints);
        pnlStats.add(lblCMC1, constraints);

        pnlStats.add(lblBlue, constraints);
        pnlStats.add(lblEnchantment, constraints);
        pnlStats.add(lblCMC2, constraints);

        pnlStats.add(lblGreen, constraints);
        pnlStats.add(lblInstant, constraints);
        pnlStats.add(lblCMC3, constraints);

        pnlStats.add(lblRed, constraints);
        pnlStats.add(lblLand, constraints);
        pnlStats.add(lblCMC4, constraints);

        pnlStats.add(lblWhite, constraints);
        pnlStats.add(lblPlaneswalker, constraints);
        pnlStats.add(lblCMC5, constraints);

        pnlStats.add(lblColorless, constraints);
        pnlStats.add(lblSorcery, constraints);
        pnlStats.add(lblCMC6, constraints);
    }

    //========== Overridden methods

    /* (non-Javadoc)
     * @see forge.gui.framework.IVDoc#getDocumentID()
     */
    @Override
    public EDocID getDocumentID() {
        return EDocID.EDITOR_STATISTICS;
    }

    /* (non-Javadoc)
     * @see forge.gui.framework.IVDoc#getTabLabel()
     */
    @Override
    public DragTab getTabLabel() {
        return tab;
    }

    /* (non-Javadoc)
     * @see forge.gui.framework.IVDoc#getLayoutControl()
     */
    @Override
    public CStatistics getLayoutControl() {
        return CStatistics.SINGLETON_INSTANCE;
    }

    /* (non-Javadoc)
     * @see forge.gui.framework.IVDoc#setParentCell(forge.gui.framework.DragCell)
     */
    @Override
    public void setParentCell(final DragCell cell0) {
        this.parentCell = cell0;
    }

    /* (non-Javadoc)
     * @see forge.gui.framework.IVDoc#getParentCell()
     */
    @Override
    public DragCell getParentCell() {
        return this.parentCell;
    }

    /* (non-Javadoc)
     * @see forge.gui.framework.IVDoc#populate()
     */
    @Override
    public void populate() {
        parentCell.getBody().setLayout(new MigLayout("insets 0, gap 0"));
        parentCell.getBody().add(scroller, "w 96%!, h 96%!, gap 2% 0 2% 0");
    }

    //========== Retrieval methods

    /** @return {@link javax.swing.JLabel} */
    public JLabel getLblMulti() { return lblMulti; }
    /** @return {@link javax.swing.JLabel} */
    public JLabel getLblBlack() { return lblBlack; }
    /** @return {@link javax.swing.JLabel} */
    public JLabel getLblBlue() { return lblBlue; }
    /** @return {@link javax.swing.JLabel} */
    public JLabel getLblGreen() { return lblGreen; }
    /** @return {@link javax.swing.JLabel} */
    public JLabel getLblRed() { return lblRed; }
    /** @return {@link javax.swing.JLabel} */
    public JLabel getLblWhite() { return lblWhite; }
    /** @return {@link javax.swing.JLabel} */
    public JLabel getLblColorless() { return lblColorless; }

    /** @return {@link javax.swing.JLabel} */
    public JLabel getLblArtifact() { return lblArtifact; }
    /** @return {@link javax.swing.JLabel} */
    public JLabel getLblEnchantment() { return lblEnchantment; }
    /** @return {@link javax.swing.JLabel} */
    public JLabel getLblCreature() { return lblCreature; }
    /** @return {@link javax.swing.JLabel} */
    public JLabel getLblSorcery() { return lblSorcery; }
    /** @return {@link javax.swing.JLabel} */
    public JLabel getLblInstant() { return lblInstant; }
    /** @return {@link javax.swing.JLabel} */
    public JLabel getLblPlaneswalker() { return lblPlaneswalker; }
    /** @return {@link javax.swing.JLabel} */
    public JLabel getLblLand() { return lblLand; }

    /** @return {@link javax.swing.JLabel} */
    public JLabel getLblCMC0() { return lblCMC0; }
    /** @return {@link javax.swing.JLabel} */
    public JLabel getLblCMC1() { return lblCMC1; }
    /** @return {@link javax.swing.JLabel} */
    public JLabel getLblCMC2() { return lblCMC2; }
    /** @return {@link javax.swing.JLabel} */
    public JLabel getLblCMC3() { return lblCMC3; }
    /** @return {@link javax.swing.JLabel} */
    public JLabel getLblCMC4() { return lblCMC4; }
    /** @return {@link javax.swing.JLabel} */
    public JLabel getLblCMC5() { return lblCMC5; }
    /** @return {@link javax.swing.JLabel} */
    public JLabel getLblCMC6() { return lblCMC6; }
    /** @return {@link javax.swing.JLabel} */
    public JLabel getLblTotal() { return lblTotal; }
    /** @return {@link javax.swing.JLabel} */
    public JLabel getLblTMC() { return lblTMC; }
    /** @return {@link javax.swing.JLabel} */
    public JLabel getLblAMC() { return lblAMC; }

    //========== Other methods

    private JLabel buildLabel(ImageIcon icon, boolean zebra) {
        final JLabel lbl = new FLabel.Builder().text("0")
                .icon(icon).iconScaleAuto(false)
                .fontSize(11).build();

        if (zebra) {
            lbl.setOpaque(true);
            lbl.setBackground(FSkin.getColor(FSkin.Colors.CLR_THEME2));
        }

        return lbl;
    }
    
    private JLabel buildLabel(SEditorUtil.StatTypes statType, boolean zebra) {
        return buildLabel(statType.img, zebra);
    }
}
