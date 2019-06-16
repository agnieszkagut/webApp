import React, { Component } from "react";
import {Row} from "reactstrap";
import UsersComponent from "./UsersComponent";
import { translate, Trans } from 'react-i18next';

class NewMessage extends Component{
    constructor(props) {
        super(props);
        this.state = {
            message:{
                creatorId: 0,
                recipientEmail: this.props.t('prompts.recipient', { framework: "react-i18next" }),
                subject: this.props.t('prompts.title', { framework: "react-i18next" }),
                messageBody: this.props.t('prompts.body', { framework: "react-i18next" })
            },
            users: []
        };
        this.handleChange = this.handleChange.bind(this);
    }
    componentDidMount() {
        const URL = "http://localhost:8080"
        const message = this.state.message
        message.creatorId = this.props.creatorId
        this.setState({ message: message })
        fetch(URL + "/messages/listOfUsers",{
            headers:{'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)}
        })
            .then(res => res.json())
            .then(data => {
                this.setState({ users: data })
            })
    }
    handleChange(propertyName, event) {
        const message = this.state.message
        message[propertyName] = event.target.value
        this.setState({ message: message })
    }
    myCallback = (dataFromChild) => {
        const message = this.state.message
        message.recipientEmail = dataFromChild
        this.setState({ message: message })
    }
    render(){
        return (
            <div>
                <div>
                    <h2>{this.props.t('messages.new', { framework: "react-i18next" })}</h2>
                    <Row>
                        <UsersComponent callbackFromParent={this.myCallback} users={this.state.users}/>
                    </Row>
                    <Row>
                        <input type="text" onChange={this.handleChange.bind(this, 'subject')} value={this.state.message.subject}/>
                    </Row>
                    <Row>
                        <textarea rows={10} cols={50} type="text" onChange={this.handleChange.bind(this, 'messageBody')} value={this.state.message.messageBody}/>
                    </Row>
                    <Row>
                        <button className={this.props.buttonStyle} onClick={()=>{
                            fetch('http://localhost:8080/messages/newMessage', {
                                method: 'POST',
                                headers: {
                                    'Accept': 'application/json',
                                    'Content-Type': 'application/json',
                                    'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
                                },
                                body: JSON.stringify(
                                    this.state.message)
                            })}}>
                            {this.props.t('buttons.submit', { framework: "react-i18next" })}
                        </button>
                    </Row>
                </div>
            </div>
        )
    }
}

export default translate('common')(NewMessage);