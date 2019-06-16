import React, { Component } from "react";
import { translate, Trans } from 'react-i18next';

class EmployeeItem extends Component{
    render(){
        return(
            <p>
                {this.props.realname} ({this.props.position})
            </p>
        )
    }
}

export default translate('common')(EmployeeItem);