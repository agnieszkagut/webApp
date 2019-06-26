import React, { Component } from "react";
import { translate, Trans } from 'react-i18next';
import {Row} from "react-bootstrap";

class ProjectLeaderComponent extends Component{
    constructor(props){
        super(props)
        this.state={
            showMessage: false,
            newMessage: {
                creatorId: 0,
                subject: this.props.t('messages.subject', {framework: "react-i18next"}),
                message: this.props.t('messages.message', {framework: "react-i18next"})
            }
        }
        this.handleChange = this.handleChange.bind(this);
        this.newMessage = this.newMessage.bind(this);
    }
    componentDidMount(): void {
        const newMessage = this.state.newMessage
        newMessage.creatorId = this.props.creatorId
        this.setState({ newMessage: newMessage })
    }

    handleChange(propertyName, event) {
        const newMessage = this.state.newMessage
        newMessage[propertyName] = event.target.value
        this.setState({ newMessage: newMessage })
    }
    newMessage(){

        return(
            <div>
                <Row>
                    <input type="text" onChange={this.handleChange.bind(this, 'subject')} value={this.state.newMessage.subject}/>
                </Row>
                <Row>
                    <textarea
                        type="text" rows={7} cols={40} onChange={this.handleChange.bind(this, 'message')} value={this.state.newMessage.message}
                    />
                </Row>
                <button className={this.props.buttonStyle} onClick={()=>{
                    fetch('http://localhost:8080/projects/' + this.props.projectId + "/leader/message", {
                        method: 'POST',
                        headers: {
                            'Accept': 'application/json',
                            'Content-Type': 'application/json',
                            'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
                        },
                        body: JSON.stringify(
                            this.state.message)
                    })
                        .then(this.setState({showMessage: !this.state.showMessage}))
                }}>
                    {this.props.t('buttons.send', { framework: "react-i18next" })}
                </button>
            </div>
        )
    }
    render(){
        return(
            <div>
                <div>
                    {this.props.t('project.projectLeader', { framework: "react-i18next" })}{this.props.projectLeader}
                    {(this.props.credentials.userLevel === 1 || this.props.credentials.userLevel === 0)
                        ?<button className={this.props.buttonStyle} style={{marginLeft:"20px"}} onClick={()=> this.setState({showMessage: !this.state.showMessage})}>
                        {this.state.showMessage
                            ?<span>
                                    {this.props.t('buttons.cancel', { framework: "react-i18next" })}
                            </span>
                            :<span>
                                    {this.props.t('project.message', { framework: "react-i18next" })}<i style={{color:"green"}} className="fa fa-paper-plane" aria-hidden="true"></i>
                            </span>}
                        </button>
                        :null}
                </div>
                <div>
                    {this.state.showMessage
                        ?this.newMessage()
                        :null
                    }
                </div>
            </div>
        )
    }
}

export default translate('common')(ProjectLeaderComponent);