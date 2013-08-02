package forge.gui.cardseteditor;

import javax.swing.ImageIcon;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;

import forge.Command;
import forge.card.CardRules;
import forge.card.CardRulesPredicates;
import forge.gui.cardseteditor.views.ITableContainer;
import forge.gui.cardseteditor.views.VCardCatalog;
import forge.gui.cardseteditor.views.VCurrentCard;
import forge.gui.toolbox.FLabel;
import forge.gui.toolbox.FSkin;
import forge.item.PaperCard;
import forge.item.InventoryItem;
import forge.item.ItemPoolView;
import forge.util.Aggregates;
import forge.util.TextUtil;


/** 
 * Static methods for working with top-level editor methods,
 * included but not limited to preferences IO, icon generation,
 * and stats analysis.
 *
 * <br><br>
 * <i>(S at beginning of class name denotes a static factory.)</i>
 *
 */
public final class SEditorUtil  {
    /** An enum to encapsulate metadata for the stats/filter objects. */
    public static enum StatTypes {
        TOTAL      (FSkin.ZoneImages.ICO_HAND,      null, 0),
        WHITE      (FSkin.ManaImages.IMG_WHITE,     CardRulesPredicates.Presets.IS_WHITE, 1),
        BLUE       (FSkin.ManaImages.IMG_BLUE,      CardRulesPredicates.Presets.IS_BLUE, 1),
        BLACK      (FSkin.ManaImages.IMG_BLACK,     CardRulesPredicates.Presets.IS_BLACK, 1),
        RED        (FSkin.ManaImages.IMG_RED,       CardRulesPredicates.Presets.IS_RED, 1),
        GREEN      (FSkin.ManaImages.IMG_GREEN,     CardRulesPredicates.Presets.IS_GREEN, 1),
        COLORLESS  (FSkin.ManaImages.IMG_COLORLESS, CardRulesPredicates.Presets.IS_COLORLESS, 1),
        MULTICOLOR (FSkin.EditorImages.IMG_MULTI,   CardRulesPredicates.Presets.IS_MULTICOLOR, 1),

        PACK         (FSkin.EditorImages.IMG_PACK,         null, 2),
        LAND         (FSkin.EditorImages.IMG_LAND,         CardRulesPredicates.Presets.IS_LAND, 2),
        ARTIFACT     (FSkin.EditorImages.IMG_ARTIFACT,     CardRulesPredicates.Presets.IS_ARTIFACT, 2),
        CREATURE     (FSkin.EditorImages.IMG_CREATURE,     CardRulesPredicates.Presets.IS_CREATURE, 2),
        ENCHANTMENT  (FSkin.EditorImages.IMG_ENCHANTMENT,  CardRulesPredicates.Presets.IS_ENCHANTMENT, 2),
        PLANESWALKER (FSkin.EditorImages.IMG_PLANESWALKER, CardRulesPredicates.Presets.IS_PLANESWALKER, 2),
        INSTANT      (FSkin.EditorImages.IMG_INSTANT,      CardRulesPredicates.Presets.IS_INSTANT, 2),
        SORCERY      (FSkin.EditorImages.IMG_SORCERY,      CardRulesPredicates.Presets.IS_SORCERY, 2);

        public final ImageIcon img;
        public final Predicate<CardRules> predicate;
        public final int group;

        StatTypes(FSkin.SkinProp prop, Predicate<CardRules> pred, int grp) {
            img = new ImageIcon(FSkin.getImage(prop, 18, 18));
            predicate = pred;
            group = grp;
        }

        public String toLabelString() {
            if (this == PACK) {
                return "Card packs and prebuilt cardsets";
            }
            return TextUtil.enumToLabel(this) + " cards";
        }
    }

    /**
     * Divides X by Y, multiplies by 100, rounds, returns.
     * 
     * @param x0 &emsp; Numerator (int)
     * @param y0 &emsp; Denominator (int)
     * @return rounded result (int)
     */
    public static int calculatePercentage(final int x0, final int y0) {
        return (int) Math.round((double) (x0 * 100) / (double) y0);
    }

    private static final Predicate<Object> totalPred = Predicates.instanceOf(PaperCard.class);
    private static final Predicate<Object> packPred  = Predicates.not(totalPred);
    
    /**
     * setStats.
     * 
     * @param <T> &emsp; the generic type
     * @param items &emsp; ItemPoolView<InventoryITem>
     * @param view &emsp; {@link forge.gui.cardseteditor.views.ITableContainer}
     */
    public static <T extends InventoryItem> void setStats(final ItemPoolView<T> items, final ITableContainer view) {
        for (StatTypes s : StatTypes.values()) {
            switch (s) {
            case TOTAL:
                view.getStatLabel(s).setText(String.valueOf(
                        Aggregates.sum(Iterables.filter(items, Predicates.compose(totalPred, items.FN_GET_KEY)), items.FN_GET_COUNT)));
                break;
            case PACK:
                view.getStatLabel(s).setText(String.valueOf(
                        Aggregates.sum(Iterables.filter(items, Predicates.compose(packPred, items.FN_GET_KEY)), items.FN_GET_COUNT)));
                break;
            default:
                view.getStatLabel(s).setText(String.valueOf(items.countAll(Predicates.compose(s.predicate, PaperCard.FN_GET_RULES), PaperCard.class)));
            }
        }
    }

    /**
     * Resets components that may have been changed
     * by various configurations of the cardset editor.
     */
    @SuppressWarnings("serial")
    public static void resetUI() {
        VCardCatalog.SINGLETON_INSTANCE.getBtnAdd4().setVisible(true);
        VCurrentCard.SINGLETON_INSTANCE.getBtnRemove4().setVisible(true);

        VCurrentCard.SINGLETON_INSTANCE.getBtnSave().setVisible(true);
        VCurrentCard.SINGLETON_INSTANCE.getBtnSaveAs().setVisible(true);
        VCurrentCard.SINGLETON_INSTANCE.getBtnNew().setVisible(true);
        VCurrentCard.SINGLETON_INSTANCE.getBtnOpen().setVisible(true);

        VCurrentCard.SINGLETON_INSTANCE.getTxfTitle().setEnabled(true);
        
        VCardCatalog.SINGLETON_INSTANCE.getPnlHeader().setVisible(false);
        VCardCatalog.SINGLETON_INSTANCE.getLblTitle().setText("");

        VCurrentCard.SINGLETON_INSTANCE.getPnlHeader().setVisible(true);

        VCardCatalog.SINGLETON_INSTANCE.getTabLabel().setText("Card Catalog");

        VCurrentCard.SINGLETON_INSTANCE.getBtnPrintProxies().setVisible(true);
        VCurrentCard.SINGLETON_INSTANCE.getBtnDoSideboard().setVisible(false);

        VCurrentCard.SINGLETON_INSTANCE.getTxfTitle().setVisible(true);
        VCurrentCard.SINGLETON_INSTANCE.getLblTitle().setText("Title:");

        ((FLabel) VCurrentCard.SINGLETON_INSTANCE.getBtnSave())
            .setCommand(new Command() {
                @Override public void run() { SEditorIO.saveCard(); } });
    }
}
