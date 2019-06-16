import React, { Component } from "react";
import { translate, Trans } from 'react-i18next';

class Prompt extends  Component{

    render(){
        return (
            <div>
                <h3>
                    {this.props.t('project.jsonFile', { framework: "react-i18next" })}
                </h3>
                <div>
                    {"\{"}
                        <p style={{marginLeft: "5em"}}>
                            name: String,<br/>
                            leaderEmail: String,<br/>
                            description: String,<br/>
                            sponsorship: int,<br/>
                            endDate: Date(yyyy-MM-dd)
                            </p>
                    {"\}"}
                </div>
            </div>
        )
    }
}

export default translate('common')(Prompt);