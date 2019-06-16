import {Col, Row} from "reactstrap";
import React, { Component } from "react";
import IssueHistoryComponent from "./IssueHistoryComponent";
import { translate, Trans } from 'react-i18next';

class IssueComponent extends Component{
    constructor(props) {
        super(props);
        this.state = {showForm: false,
            showDetails: false,
            issueHistory: [],
            newInscription: this.props.t('prompts.inscription', { framework: "react-i18next" })};
        this.handleChange = this.handleChange.bind(this);
    }
    componentDidMount() {
        const URL = "http://localhost:8080";
        fetch(URL + "/issues/" + this.props.issueId,{
            headers: {
            'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
        }})
            .then(res => res.json())
            .then(data => {
                this.setState({ issueHistory: data })
            });
    }
    handleChange(event) {
        this.setState({ newInscription: event.target.value });
    }
    render(){
        return(
            <div>
                <Row >
                    <Col xs="3" />
                    <Col xs="12" sm="10">
                        <h3>{this.props.t('issue.subject', { framework: "react-i18next" })}{this.props.title}</h3>
                        {this.props.t('issue.status', { framework: "react-i18next" })}{this.props.status}
                        {
                            this.state.showDetails?
                                <div>
                                    {
                                        this.state.issueHistory.map(function (issue, index) {
                                                return (
                                                    <div key={index}
                                                         onClick={() => this.setState({showDetails: !this.state.showDetails})}>
                                                        <IssueHistoryComponent value={issue.newValue} key={index}/>
                                                    </div>
                                                )
                                            }
                                        )
                                    }</div>
                                :null
                        }
                    </Col>
                </Row>
                <Row>
                    {this.state.showForm ?
                        <div>
                                <textarea rows={10} cols={50} className="textarea" value={this.state.newInscription} onChange={this.handleChange}
                                />
                        </div>
                        :null
                    }
                </Row>
                <Row>
                    <Col>
                        {this.state.showForm?
                            (<div>
                                <button style={{border: "1px solid white"}} className={this.props.buttonStyle} onClick={()=>{
                                    fetch('http://localhost:8080/issues/modifyIssue/' + this.props.issueId + '/' + this.props.user, {
                                        method: 'POST',
                                        headers: {'Content-Type': 'application/json',
                                                'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
                                            },
                                        body: JSON.stringify(
                                            this.state.newInscription)
                                    })
                                        this.setState({showForm:!this.state.showForm})
                                    }}>
                                        {this.props.t('buttons.submit', { framework: "react-i18next" })}
                                </button>
                                <button style={{border: "1px solid white"}} className={this.props.buttonStyle} onClick={()=>
                                    this.setState({showForm:!this.state.showForm})}>
                                    {this.props.t('buttons.cancel', { framework: "react-i18next" })}
                                </button>
                            </div>)
                            :<button style={{border: "1px solid white"}} className={this.props.buttonStyle} onClick={()=>
                                this.setState({showForm:!this.state.showForm})}>
                                <i style={{color:"white"}} className="fas fa-pencil-alt"></i>
                            </button>
                        }
                        <button style={{border: "1px solid white"}} className={this.props.buttonStyle} onClick={()=>this.setState({showDetails:!this.state.showDetails})}>
                            {this.state.showDetails?
                                <span>{this.props.t('buttons.details.hide', { framework: "react-i18next" })}</span>
                                :<span>{this.props.t('buttons.details.show', { framework: "react-i18next" })}</span>
                            }
                        </button>
                    </Col>
                </Row>
            </div>
        )
    }
}

export default translate('common')(IssueComponent);