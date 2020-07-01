package com.kristenmabry.ledmatrix;

public class MatrixFileLayout {
    private LayoutTypes type;
    private String name;
    private int sortOrder;
    private String line1;
    private String line2;
    private int[][] colors1;
    private int[][] colors2;

    public MatrixFileLayout(MatrixTextLayout layout) {
        this.type = LayoutTypes.Text;
        this.name = layout.getName();
        this.sortOrder = layout.getSortOrder();
        this.line1 = layout.getLine1();
        this.line2 = layout.getLine2();
        this.colors1 = layout.getColors1();
        this.colors2 = layout.getColors2();
    }
}
