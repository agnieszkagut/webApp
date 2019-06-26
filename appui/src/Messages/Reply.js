import React, { Component} from "react";
import { translate, Trans } from 'react-i18next';

class Reply extends Component{
    constructor(props) {
        super(props)
        this.state = {
            replyMessage:{
                user: 0,
                recipientId: 0,
                parentMessageId: 0,
                subject: this.props.t('prompts.subjectMessage', { framework: "react-i18next" }),
                message: this.props.t('prompts.reply', { framework: "react-i18next" })
            }}
        this.handleChange = this.handleChange.bind(this);
        this.submitFunction = this.submitFunction.bind(this);
    }
    submitFunction(){
        fetch('http://localhost:8080/messages/' + this.state.replyMessage.parentMessageId + "/replies", {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
            },
            body: JSON.stringify(
                this.state.replyMessage.user,
                this.state.replyMessage.recipientId,
                this.state.replyMessage.subject,
                this.state.replyMessage.message)
        })
    }
    handleChange(propertyName, event) {
        const replyMessage = this.state.replyMessage
        replyMessage[propertyName] = event.target.value
        this.setState({ replyMessage: replyMessage })
    }
    componentDidMount(){
        const replyMessage = this.state.replyMessage
        replyMessage.user = this.props.user
        replyMessage.subject = this.props.subject
        replyMessage.recipientId = this.props.creatorId
        replyMessage.parentMessageId = this.props.parentMessageId
        this.setState({ replyMessage: replyMessage })
    }
    componentDidUpdate(){
        if(this.props.doSubmit){
            this.submitFunction()
            this.props.callbackFromParent()
        }
    }

    render(){
        return(
            <div>
                <div>
                    <input type="text" onChange={this.handleChange.bind(this, 'subject')} value={this.state.replyMessage.subject}/>
                </div>
                <div>
                    <textarea rows={10} cols={50} type="text" onChange={this.handleChange.bind(this, 'message')} value={this.state.replyMessage.message}/>
                </div>

            </div>
        )
    }
}

export default translate('common')(Reply);