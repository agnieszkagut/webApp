import React, { Component } from "react";
import { translate, Trans } from 'react-i18next';

class UsersComponent extends Component{
    constructor(props) {
        super(props);
        this.state = {
            selectedUser: ''
        };
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(event) {
        this.setState({selectedUser: event.target.value})
        this.props.callbackFromParent(event.target.value)
    }
    render () {
        let optionItems = this.props.users.map((user, index) =>
            <option key={index} value={user.email}> {user.realname} ({user.email})</option>
        )
        const firstOne = <option key='key' value=''>{this.props.t('prompts.user', { framework: "react-i18next" })}</option>
        optionItems.unshift(firstOne)
        return (
            <div>
                <select style={{width: "200px"}} value={this.state.selectedUser} onChange={this.handleChange}>
                    {optionItems}
                </select>
            </div>
        )
    }
}

export default translate('common')(UsersComponent);