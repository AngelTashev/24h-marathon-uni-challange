package models.enums;

public enum ProductCategory {
    PRODUCT_FOOD {
        @Override
        public String toString() {
            return "Food product";
        }
    },
    PRODUCT_NONFOOD {
        @Override
        public String toString() {
            return "Non-food product";
        }
    },
}
