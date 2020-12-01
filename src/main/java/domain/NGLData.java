package domain;

public class NGLData {
        String fluidName;
        Double y60;
        Double tC;
        Double zC;
        Double pc;
        Double k1;
        Double k2;
        Double k3;
        Double k4;
        Double trx;
        Double tr60;
        Double tauRx;
        Integer sign;
        Double tau60;
        Double rosat;
        Double rosat60;
        Double yxref;

        public NGLData(String fluidName, Double y60, Double tC, Double zC, Double pc, Double k1, Double k2, Double k3, Double k4, Double trx, Double tr60, Double tauRx, Integer sign, Double tau60, Double rosat, Double rosat60, Double yxref) {
            this.fluidName = fluidName;
            this.y60 = y60;
            this.tC = tC;
            this.zC = zC;
            this.pc = pc;
            this.k1 = k1;
            this.k2 = k2;
            this.k3 = k3;
            this.k4 = k4;
            this.trx = trx;
            this.tr60 = tr60;
            this.tauRx = tauRx;
            this.sign = sign;
            this.tau60 = tau60;
            this.rosat = rosat;
            this.rosat60 = rosat60;
            this.yxref = yxref;
        }

        public String getFluidName() {
            return fluidName;
        }

        public Double getY60() {
            return y60;
        }

        public Double gettC() {
            return tC;
        }

        public Double getzC() {
            return zC;
        }

        public Double getPc() {
            return pc;
        }

        public Double getK1() {
            return k1;
        }

        public Double getK2() {
            return k2;
        }

        public Double getK3() {
            return k3;
        }

        public Double getK4() {
            return k4;
        }

        public Double getTrx() {
            return trx;
        }

        public Double getTr60() {
            return tr60;
        }

        public Double getTauRx() {
            return tauRx;
        }

        public Integer getSign() {
            return sign;
        }

        public Double getTau60() {
            return tau60;
        }

        public Double getRosat() {
            return rosat;
        }

        public Double getRosat60() {
            return rosat60;
        }

        public Double getYxref() {
            return yxref;
        }

}
