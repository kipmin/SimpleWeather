package com.kipmin.simpleweather.Gson.Weather;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yzl91 on 2017/8/12.
 */

public class Suggestion {

    public Comf comf;

    public class Comf {

        @SerializedName("brf")
        public String brfComf;

        @SerializedName("txt")
        public String txtComf;
    }

    public Cw cw;

    public class Cw {

        @SerializedName("brf")
        public String brfCw;

        @SerializedName("txt")
        public String txtCw;
    }

    public Drsg drsg;

    public class Drsg {

        @SerializedName("brf")
        public String brfDrsg;

        @SerializedName("txt")
        public String txtDrsg;
    }

    public Flu flu;

    public class Flu {

        @SerializedName("brf")
        public String brfFlu;

        @SerializedName("txt")
        public String txtFlu;
    }

    public Sport sport;

    public class Sport {

        @SerializedName("brf")
        public String brfSport;

        @SerializedName("txt")
        public String txtSport;
    }

    public Trav trav;

    public class Trav {

        @SerializedName("brf")
        public String brfTrav;

        @SerializedName("txt")
        public String txtTrav;
    }

    public Uv uv;

    public class Uv {

        @SerializedName("brf")
        public String brfUv;

        @SerializedName("txt")
        public String txtUv;
    }
}
