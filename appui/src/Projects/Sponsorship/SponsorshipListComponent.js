import React, { Component } from "react";
import { translate, Trans } from 'react-i18next';
import SponsorshipItem from "./SponsorshipItem";

class SponsorshipListComponent extends Component{
    render(){
        return(
            <div>
                <ul>
                    {this.props.listOfSponsorships.map(function(sponsorship,index) {
                            return (
                                <div key={index}>
                                    <SponsorshipItem
                                        key={index}
                                        name={sponsorship.name}
                                        value={sponsorship.value}
                                    />
                                </div>
                            )
                        }
                    )}
                </ul>
                {this.props.t('project.unassigned', { framework: "react-i18next" })} {this.props.unassignedFunds}
            </div>
        )
    }
}

export default translate('common')(SponsorshipListComponent);