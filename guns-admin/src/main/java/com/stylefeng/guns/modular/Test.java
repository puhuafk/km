package com.stylefeng.guns.modular;

import java.util.List;

public class Test {


    /**
     * animals : {"dog":[{"name":"Rufus","breed":"labrador","count":1,"twoFeet":false},{"name":"Marty","breed":"whippet","count":1,"twoFeet":false}],"cat":{"name":"Matilda"}}
     */

    private AnimalsBean animals;

    public AnimalsBean getAnimals() {
        return animals;
    }

    public void setAnimals(AnimalsBean animals) {
        this.animals = animals;
    }

    public static class AnimalsBean {
        /**
         * dog : [{"name":"Rufus","breed":"labrador","count":1,"twoFeet":false},{"name":"Marty","breed":"whippet","count":1,"twoFeet":false}]
         * cat : {"name":"Matilda"}
         */

        private CatBean cat;
        private List<DogBean> dog;

        public CatBean getCat() {
            return cat;
        }

        public void setCat(CatBean cat) {
            this.cat = cat;
        }

        public List<DogBean> getDog() {
            return dog;
        }

        public void setDog(List<DogBean> dog) {
            this.dog = dog;
        }

        public static class CatBean {
            /**
             * name : Matilda
             */

            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class DogBean {
            /**
             * name : Rufus
             * breed : labrador
             * count : 1
             * twoFeet : false
             */

            private String name;
            private String breed;
            private int count;
            private boolean twoFeet;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getBreed() {
                return breed;
            }

            public void setBreed(String breed) {
                this.breed = breed;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public boolean isTwoFeet() {
                return twoFeet;
            }

            public void setTwoFeet(boolean twoFeet) {
                this.twoFeet = twoFeet;
            }
        }
    }


}
