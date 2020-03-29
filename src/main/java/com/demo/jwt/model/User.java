package com.demo.jwt.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "users")
public class User
{

	@Id
	@Column(name = "user_id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "enabled")
	private Boolean enabled;

	@ManyToMany(cascade =
	{ CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns =
	{ @JoinColumn(name = "user_id") }, inverseJoinColumns =
	{ @JoinColumn(name = "role_id") })
	@JsonIgnoreProperties("users")
	private Set<Role> roles;

	private String token;

	public User()
	{
		this.enabled = false;
	}

	public User(String username, String password)
	{
		this.username = username;
		this.password = password;
		this.enabled = false;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public Boolean getEnabled()
	{
		return enabled;
	}

	public void setEnabled(Boolean enabled)
	{
		this.enabled = enabled;
	}

	public Set<Role> getRoles()
	{
		return roles;
	}

	public void setRoles(Set<Role> roles)
	{
		this.roles = roles;
	}

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}

//

	public void addRole(Role role)
	{
		if (this.roles == null || this.roles.isEmpty())
		{
			this.roles = new HashSet<Role>();
		}

		this.roles.add(role);
	}

}