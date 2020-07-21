<template>
    <div class="modal-wrapper" :class="wrapper_classes">
        <div @click="toggle" class="trigger" :class="button_classes">{{button_text}}</div>
        <div class="modal" v-if="open">
            <div class="modal-backdrop" @click="toggle"></div>
            <div class="card" :style="{height:cardHeight}">
                <div class="card-header" v-show="show_header">
                    <h5 :class="['m-0', title_classes]">{{title}}</h5>
                    <button @click="toggle" class="btn btn-link btn-close btn-lg p-0">&times;</button>
                </div>
                <div class="card-body" :style="{padding:cardBodyPadding}">
                    <slot/>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: 'Modal',
        data: function () {
            return {
                open: false
            }
        },
        props: {
            title: String,
            button_text: String,
            button_classes: String,
            title_classes: String,
            wrapper_classes: String,
            cardHeight: String,
            cardBodyPadding: String,
            show_header: {
                type: Boolean,
                default: true,
            },
            disable_background_click_to_close: {
                type: Boolean,
                default: false,
            }
        },
        methods: {
            toggle () {
                if (this.disable_background_click_to_close){
                    return;
                }
                this.open = !this.open
            },
            openModal(){
                this.open = true;
            },
            closeModal(){
                this.open = false;
            },
            callback(functionName){
                functionName();
            }
        }
    }
</script>

<style lang="scss" scoped>

    .modal-wrapper {
        display: inline-block;
    }
    .trigger {
        cursor: pointer;
    }
    .cancel-btn {
        background-color: #dc3545;
        border-color: #dc3545;
        color: #fff;
    }
    .title-red {
        color: #dc3545;
    }
    .modal {
        position: fixed;
        top: 0;
        left: 0;
        height: 100%;
        width: 100%;
        z-index: 3000;
        display: flex;
        align-items: center;
        justify-content: center;
        padding: 1rem;
        animation: modal-fade 300ms ease forwards;
        opacity: 0;
    }
    .modal-backdrop {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        /*background: rgba(#fafafa, 0.9);*/
        background: rgba(0, 0, 0, 0.6);
    }
    .card {
        /*flex: 1 1 auto;*/
        flex: inherit;
        /*max-width: 500px;*/
        max-height: 95%;
        -webkit-overflow-scrolling: touch;
        /*overflow-y: scroll;*/
        animation: card-slide 300ms ease forwards;
        animation-delay: 100ms;
        opacity: 0;
        transform: translate(0, 3rem);
        z-index: 2000;
        border-radius:15px;
    }
    .card-header {
        padding: 1rem;
        flex: 0 0 auto;
        display: flex;
        justify-content: space-between;
        align-items: center;
        h5 {
            margin: 1rem 0;
        }
        .btn-link {
            color: black;
            display: flex;
            align-items: center;
        }
    }
    @keyframes modal-fade {
        to {
            opacity: 1;
        }
    }
    @keyframes card-slide {
        to {
            opacity: 1;
            transform: translate(0,0);
        }
    }
</style>
