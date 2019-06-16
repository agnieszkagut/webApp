import React, { Component } from "react";
import { translate, Trans } from 'react-i18next';

class IssueHistoryComponent extends Component{
    render(){
        return(
            <p>{this.props.value}</p>
        )
    }
}

export default translate('common')(IssueHistoryComponent);