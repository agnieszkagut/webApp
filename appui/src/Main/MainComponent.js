import React, { Component } from "react";
import IssueComponent from "../reportedIssues/IssueComponent";
import {Row} from "reactstrap";
import { translate, Trans } from 'react-i18next';

class MainComponent extends  Component{
    constructor(props) {
        super(props);
        this.state = {issues: []};
    }
    componentDidMount() {
        const URL = "http://localhost:8080"
        fetch(URL + "/issues/lastThree",
            {
                method: 'GET',
                headers: {
                    'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
                }
            }).then(res => res.json())
            .then(data => {
                this.setState({ issues: data })
        });
    }

    render(){
        const credentials = this.props.credentials
        const userId = this.props.user
        const buttonStyle = this.props.buttonStyle
        return (
            <div>
                <Row>
                    <ul>
                        {this.state.issues.map(function(issue,index) {
                                return (
                                    <div key={index}>
                                        <IssueComponent user={userId}
                                                        issueId={issue.issueId}
                                                        status={issue.status}
                                                        title={issue.title}
                                                        key={index}
                                                        buttonStyle={buttonStyle}
                                                        credentials={credentials}
                                        />
                                    </div>
                                )
                            }
                        )}
                    </ul>
                </Row>
            </div>
        )
    }
}


export default translate('common')(MainComponent);