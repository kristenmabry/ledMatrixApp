package com.kristenmabry.ledmatrix.layouts;

import java.util.Arrays;
import java.util.Comparator;

public class MatrixFileLayout {
    private LayoutTypes type;
    private MatrixTextLayout textLayout;

    public MatrixFileLayout(MatrixTextLayout layout) {
        this.type = LayoutTypes.Text;
        this.textLayout = layout;
    }

    public MatrixTextLayout getLayout() {
        return this.textLayout;
    }

    public String getDisplayName() {
        return this.textLayout.getName() + (this.type == LayoutTypes.Text ? " (Text)" : " (Custom)");
    }

    public String getSubtitle() {
        return this.type == LayoutTypes.Text ? "Text" : "Custom";
    }

    public LayoutTypes getType() {
        return type;
    }

    public static MatrixFileLayout[] sortForDisplay(MatrixFileLayout[] layouts) {
        Comparator comparator = new Comparator<MatrixFileLayout>() {
            @Override
            public int compare(MatrixFileLayout a, MatrixFileLayout b) {
                return a.getLayout().getSortOrder() < b.getLayout().getSortOrder() ? -1
                        : a.getLayout().getName().compareTo(b.getLayout().getName());
            }
        };
        Arrays.sort(layouts, comparator);
        return layouts;
    }

}
