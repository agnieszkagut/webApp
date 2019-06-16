import React, { Component } from "react";
import { translate, Trans } from 'react-i18next';
import TimeComponent from "./TimeComponent";

class Time extends Component{
    constructor(props){
        super(props)
        this.state={
            showWebService: false
        }
    }
    render(){
        return(
            <div>
                <button className={this.props.buttonStyle} onClick={() => this.setState({showWebService: !this.state.showWebService})}>
                    {this.state.showWebService
                        ? this.props.t('buttons.webservice.hide', {framework: "react-i18next"})
                        : this.props.t('buttons.webservice.show', {framework: "react-i18next"})
                }
                </button>
                {this.state.showWebService
                    ? <TimeComponent/>
                    : null
                }
            </div>
        )
    }
}

export default translate('common')(Time);