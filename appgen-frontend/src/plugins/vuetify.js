import Vue from 'vue';
import Vuetify from 'vuetify';
import 'vuetify/dist/vuetify.min.css';
import colors from 'vuetify/lib/util/colors';

Vue.use(Vuetify);

export default new Vuetify({
    // theme: {
    //     themes: {
    //         light: {
    //             primary: '#f44336',
    //             secondary: '#607d8b',
    //             accent: '#795548',
    //             error: '#ffc107',
    //             warning: '#ff5722',
    //             info: '#cddc39',
    //             success: '#4caf50'
    //         },
    //     },
    // },

    theme: {
        themes: {
            light: {
                primary: colors.red.lighten1,
                secondary: colors.grey.darken1,
                accent: colors.shades.white,
                error: colors.red.accent3,
            },
            dark: {
                primary: colors.blue.lighten3,
            },
        },
    },
});
