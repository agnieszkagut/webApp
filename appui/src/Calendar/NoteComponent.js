import React, { Component } from "react";
import { translate, Trans } from 'react-i18next';

class NoteComponent extends Component{
    render(){
        console.log(this.props.value)
        return(
            <p>{this.props.value}</p>
        )
    }
}

export default translate('common')(NoteComponent);