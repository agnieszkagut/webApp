import React, { Component } from "react";
import { translate, Trans } from 'react-i18next';

class SponsorshipItem extends Component{
    render(){
        return(
            <p>
                {this.props.name}: {this.props.value}
            </p>
        )
    }
}

export default translate('common')(SponsorshipItem);